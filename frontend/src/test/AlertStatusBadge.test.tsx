import { describe, it, expect } from 'vitest'
import { render, screen } from '@testing-library/react'
import { AlertStatusBadge, SeverityBadge } from '@/components/AlertStatusBadge'

describe('AlertStatusBadge', () => {
  it('renders ACTIVE badge with red styling', () => {
    render(<AlertStatusBadge status="ACTIVE" />)
    const badge = screen.getByText('ACTIVE')
    expect(badge).toBeInTheDocument()
    expect(badge.className).toContain('text-red-800')
  })

  it('renders RESOLVED badge with green styling', () => {
    render(<AlertStatusBadge status="RESOLVED" />)
    const badge = screen.getByText('RESOLVED')
    expect(badge).toBeInTheDocument()
    expect(badge.className).toContain('text-green-800')
  })
})

describe('SeverityBadge', () => {
  it('renders HIGH badge with red background', () => {
    render(<SeverityBadge severity="HIGH" />)
    const badge = screen.getByText('HIGH')
    expect(badge.className).toContain('bg-red-500')
  })

  it('renders MEDIUM badge with yellow background', () => {
    render(<SeverityBadge severity="MEDIUM" />)
    const badge = screen.getByText('MEDIUM')
    expect(badge.className).toContain('bg-yellow-400')
  })

  it('renders LOW badge with blue background', () => {
    render(<SeverityBadge severity="LOW" />)
    const badge = screen.getByText('LOW')
    expect(badge.className).toContain('bg-blue-100')
  })
})
