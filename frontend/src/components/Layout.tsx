import { Outlet, NavLink, useNavigate } from 'react-router-dom'
import { useAuth } from '@/context/AuthContext'

export default function Layout() {
  const { fullName, role, logout } = useAuth()
  const navigate = useNavigate()

  const handleLogout = () => {
    logout()
    navigate('/login')
  }

  return (
    <div className="min-h-screen bg-gray-100 flex flex-col">
      {/* Top Nav */}
      <header className="bg-blue-700 text-white shadow-md">
        <div className="max-w-7xl mx-auto px-4 py-3 flex items-center justify-between">
          <div className="flex items-center gap-3">
            <span className="text-2xl">🚌</span>
            <span className="font-bold text-lg tracking-tight">Transit Alert Dashboard</span>
          </div>
          <nav className="flex items-center gap-6 text-sm font-medium">
            <NavLink
              to="/dashboard"
              className={({ isActive }) =>
                isActive ? 'underline underline-offset-4' : 'hover:underline hover:underline-offset-4'
              }
            >
              Dashboard
            </NavLink>
            <NavLink
              to="/alerts"
              className={({ isActive }) =>
                isActive ? 'underline underline-offset-4' : 'hover:underline hover:underline-offset-4'
              }
            >
              Alerts
            </NavLink>
            <NavLink
              to="/routes"
              className={({ isActive }) =>
                isActive ? 'underline underline-offset-4' : 'hover:underline hover:underline-offset-4'
              }
            >
              Routes
            </NavLink>
          </nav>
          <div className="flex items-center gap-3 text-sm">
            <span className="opacity-75">
              {fullName} ({role})
            </span>
            <button
              onClick={handleLogout}
              className="bg-blue-800 hover:bg-blue-900 px-3 py-1 rounded text-xs font-medium transition-colors"
            >
              Logout
            </button>
          </div>
        </div>
      </header>

      {/* Main content */}
      <main className="flex-1">
        <Outlet />
      </main>
    </div>
  )
}
