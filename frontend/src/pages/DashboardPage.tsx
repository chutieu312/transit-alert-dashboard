import { useQuery } from '@tanstack/react-query'
import { Link } from 'react-router-dom'
import { alertsApi } from '@/api'

export default function DashboardPage() {
  const { data: summary, isLoading } = useQuery({
    queryKey: ['alerts', 'summary'],
    queryFn: alertsApi.summary,
  })

  const { data: recent } = useQuery({
    queryKey: ['alerts', 'recent'],
    queryFn: () => alertsApi.list({ status: 'ACTIVE', page: 0, size: 5 }),
  })

  return (
    <div className="p-6 max-w-7xl mx-auto">
      <h1 className="text-2xl font-bold text-gray-800 mb-6">Operations Dashboard</h1>

      {/* Summary cards */}
      <div className="grid grid-cols-1 sm:grid-cols-2 gap-4 mb-8">
        <div className="bg-white rounded-xl shadow p-6 border-l-4 border-red-500">
          <p className="text-sm text-gray-500 uppercase tracking-wide font-medium">Active Alerts</p>
          <p className="text-4xl font-bold text-gray-900 mt-2">
            {isLoading ? '—' : summary?.ACTIVE ?? 0}
          </p>
          <Link to="/alerts?status=ACTIVE" className="text-blue-600 text-sm mt-2 inline-block hover:underline">
            View all active →
          </Link>
        </div>
        <div className="bg-white rounded-xl shadow p-6 border-l-4 border-green-500">
          <p className="text-sm text-gray-500 uppercase tracking-wide font-medium">Resolved Alerts</p>
          <p className="text-4xl font-bold text-gray-900 mt-2">
            {isLoading ? '—' : summary?.RESOLVED ?? 0}
          </p>
          <Link to="/alerts?status=RESOLVED" className="text-blue-600 text-sm mt-2 inline-block hover:underline">
            View resolved →
          </Link>
        </div>
      </div>

      {/* Recent active alerts */}
      <div className="bg-white rounded-xl shadow p-6">
        <div className="flex items-center justify-between mb-4">
          <h2 className="text-lg font-semibold text-gray-800">Recent Active Alerts</h2>
          <Link to="/alerts/new" className="bg-blue-600 hover:bg-blue-700 text-white text-sm px-4 py-2 rounded-lg font-medium transition-colors">
            + New Alert
          </Link>
        </div>

        {recent?.content.length === 0 && (
          <p className="text-gray-400 text-sm">No active alerts. All clear! ✅</p>
        )}

        <ul className="divide-y divide-gray-100">
          {recent?.content.map((alert) => (
            <li key={alert.id} className="py-3">
              <Link to={`/alerts/${alert.id}`} className="group flex items-start justify-between gap-3">
                <div className="flex-1 min-w-0">
                  <p className="font-medium text-gray-800 group-hover:text-blue-600 truncate">{alert.title}</p>
                  <p className="text-xs text-gray-500 mt-0.5">
                    Route {alert.affectedRouteName} · {new Date(alert.createdAt).toLocaleString()}
                  </p>
                </div>
                <span className={`shrink-0 inline-flex items-center px-2 py-0.5 rounded text-xs font-bold ${
                  alert.severity === 'HIGH' ? 'bg-red-500 text-white' :
                  alert.severity === 'MEDIUM' ? 'bg-yellow-400 text-yellow-900' :
                  'bg-blue-100 text-blue-800'
                }`}>
                  {alert.severity}
                </span>
              </Link>
            </li>
          ))}
        </ul>
      </div>
    </div>
  )
}
