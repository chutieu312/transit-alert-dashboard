import { describe, it, expect, vi, beforeEach } from 'vitest'
import { render, screen, waitFor } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { MemoryRouter } from 'react-router-dom'
import LoginPage from '@/pages/LoginPage'
import { AuthContext } from '@/context/AuthContext'

const mockLogin = vi.fn()
const mockNavigate = vi.fn()

vi.mock('react-router-dom', async () => {
  const actual = await vi.importActual('react-router-dom')
  return { ...actual, useNavigate: () => mockNavigate }
})

function renderLoginPage(loginImpl = mockLogin) {
  return render(
    <MemoryRouter>
      <AuthContext.Provider
        value={{
          token: null,
          fullName: null,
          role: null,
          isAuthenticated: false,
          login: loginImpl,
          logout: vi.fn(),
        }}
      >
        <LoginPage />
      </AuthContext.Provider>
    </MemoryRouter>,
  )
}

describe('LoginPage', () => {
  beforeEach(() => {
    mockLogin.mockReset()
    mockNavigate.mockReset()
  })

  it('renders email and password fields', () => {
    renderLoginPage()
    expect(screen.getByLabelText(/email/i)).toBeInTheDocument()
    expect(screen.getByLabelText(/password/i)).toBeInTheDocument()
  })

  it('navigates to /dashboard on successful login', async () => {
    mockLogin.mockResolvedValueOnce(undefined)
    renderLoginPage()

    await userEvent.type(screen.getByLabelText(/email/i), 'operator@transit.demo')
    await userEvent.type(screen.getByLabelText(/password/i), 'demo1234')
    await userEvent.click(screen.getByRole('button', { name: /sign in/i }))

    await waitFor(() => {
      expect(mockLogin).toHaveBeenCalledWith('operator@transit.demo', 'demo1234')
      expect(mockNavigate).toHaveBeenCalledWith('/dashboard')
    })
  })

  it('shows error message on failed login', async () => {
    mockLogin.mockRejectedValueOnce(new Error('Unauthorized'))
    renderLoginPage()

    await userEvent.type(screen.getByLabelText(/email/i), 'bad@test.com')
    await userEvent.type(screen.getByLabelText(/password/i), 'wrongpass')
    await userEvent.click(screen.getByRole('button', { name: /sign in/i }))

    await waitFor(() => {
      expect(screen.getByRole('alert')).toHaveTextContent('Invalid email or password.')
    })
  })
})
