import { useParams, Link, useNavigate } from 'react-router-dom'
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { alertsApi } from '@/api'
import { AlertStatusBadge, SeverityBadge } from '@/components/AlertStatusBadge'

export default function AlertDetailPage() {
  const { id } = useParams<{ id: string }>()
  const navigate = useNavigate()
  const queryClient = useQueryClient()

  const { data: alert, isLoading } = useQuery({
    queryKey: ['alert', id],
    queryFn: () => alertsApi.getById(id!),
    enabled: !!id,
  })

  const resolveMutation = useMutation({
    mutationFn: () => alertsApi.resolve(id!),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['alerts'] })
      queryClient.invalidateQueries({ queryKey: ['alert', id] })
    },
  })

  const deleteMutation = useMutation({
    mutationFn: () => alertsApi.delete(id!),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['alerts'] })
      navigate('/alerts')
    },
  })

  if (isLoading) return <div className="p-10 text-center text-gray-400">Loading…</div>
  if (!alert) return <div className="p-10 text-center text-gray-400">Alert not found.</div>

  return (
    <div className="p-6 max-w-3xl mx-auto">
      <Link to="/alerts" className="text-blue-600 text-sm hover:underline mb-4 inline-block">
        ← Back to Alerts
      </Link>

      <div className="bg-white rounded-xl shadow p-6">
        <div className="flex items-start justify-between gap-4">
          <h1 className="text-xl font-bold text-gray-800">{alert.title}</h1>
          <div className="flex gap-2 shrink-0">
            <SeverityBadge severity={alert.severity} />
            <AlertStatusBadge status={alert.status} />
          </div>
        </div>

        <p className="text-gray-600 mt-4">{alert.description ?? 'No description provided.'}</p>

        <dl className="mt-6 grid grid-cols-2 gap-4 text-sm">
          <div>
            <dt className="text-gray-500 font-medium">Affected Route</dt>
            <dd className="text-gray-800 mt-1">{alert.affectedRouteName}</dd>
          </div>
          <div>
            <dt className="text-gray-500 font-medium">Created By</dt>
            <dd className="text-gray-800 mt-1">{alert.createdByEmail}</dd>
          </div>
          <div>
            <dt className="text-gray-500 font-medium">Created At</dt>
            <dd className="text-gray-800 mt-1">{new Date(alert.createdAt).toLocaleString()}</dd>
          </div>
          {alert.resolvedAt && (
            <div>
              <dt className="text-gray-500 font-medium">Resolved At</dt>
              <dd className="text-gray-800 mt-1">{new Date(alert.resolvedAt).toLocaleString()}</dd>
            </div>
          )}
        </dl>

        <div className="mt-8 flex gap-3">
          {alert.status === 'ACTIVE' && (
            <button
              onClick={() => resolveMutation.mutate()}
              disabled={resolveMutation.isPending}
              className="bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded-lg text-sm font-medium disabled:opacity-50 transition-colors"
            >
              {resolveMutation.isPending ? 'Resolving…' : '✓ Mark Resolved'}
            </button>
          )}
          <button
            onClick={() => {
              if (window.confirm('Delete this alert?')) deleteMutation.mutate()
            }}
            disabled={deleteMutation.isPending}
            className="bg-red-50 hover:bg-red-100 text-red-700 border border-red-200 px-4 py-2 rounded-lg text-sm font-medium disabled:opacity-50 transition-colors"
          >
            Delete
          </button>
        </div>
      </div>
    </div>
  )
}
