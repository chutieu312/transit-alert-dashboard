import { useQuery } from '@tanstack/react-query'
import { routesApi } from '@/api'

export default function RoutesPage() {
  const { data, isLoading } = useQuery({
    queryKey: ['routes'],
    queryFn: () => routesApi.list({ page: 0, size: 50 }),
  })

  return (
    <div className="p-6 max-w-7xl mx-auto">
      <h1 className="text-2xl font-bold text-gray-800 mb-6">Routes</h1>

      {isLoading && <div className="p-10 text-center text-gray-400">Loading…</div>}

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
        {data?.content.map((route) => (
          <div key={route.id} className="bg-white rounded-xl shadow p-5">
            <div className="flex items-center justify-between mb-2">
              <span className="text-2xl font-bold text-blue-600">{route.routeNumber}</span>
              <div className="flex gap-2 items-center">
                <span className="text-xs bg-gray-100 text-gray-600 px-2 py-0.5 rounded font-medium">
                  {route.type}
                </span>
                <span className={`text-xs px-2 py-0.5 rounded font-medium ${
                  route.active ? 'bg-green-100 text-green-700' : 'bg-gray-100 text-gray-400'
                }`}>
                  {route.active ? 'Active' : 'Inactive'}
                </span>
              </div>
            </div>
            <p className="font-semibold text-gray-800">{route.name}</p>
            {route.description && (
              <p className="text-sm text-gray-500 mt-1 line-clamp-2">{route.description}</p>
            )}
          </div>
        ))}
      </div>
    </div>
  )
}
