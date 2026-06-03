import { useState, type FormEvent } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { alertsApi, routesApi } from '@/api'
import type { AlertSeverity } from '@/types'

export default function CreateAlertPage() {
  const navigate = useNavigate()
  const queryClient = useQueryClient()

  const [title, setTitle] = useState('')
  const [description, setDescription] = useState('')
  const [severity, setSeverity] = useState<AlertSeverity>('MEDIUM')
  const [routeId, setRouteId] = useState('')
  const [errors, setErrors] = useState<Record<string, string>>({})

  const { data: routes } = useQuery({
    queryKey: ['routes', 'active'],
    queryFn: routesApi.listActive,
  })

  const createMutation = useMutation({
    mutationFn: alertsApi.create,
    onSuccess: (alert) => {
      queryClient.invalidateQueries({ queryKey: ['alerts'] })
      navigate(`/alerts/${alert.id}`)
    },
  })

  const validate = () => {
    const errs: Record<string, string> = {}
    if (!title.trim()) errs.title = 'Title is required'
    if (!routeId) errs.routeId = 'Please select a route'
    return errs
  }

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault()
    const errs = validate()
    if (Object.keys(errs).length > 0) {
      setErrors(errs)
      return
    }
    createMutation.mutate({ title, description, severity, affectedRouteId: routeId })
  }

  return (
    <div className="p-6 max-w-2xl mx-auto">
      <Link to="/alerts" className="text-blue-600 text-sm hover:underline mb-4 inline-block">
        ← Back to Alerts
      </Link>

      <div className="bg-white rounded-xl shadow p-6">
        <h1 className="text-xl font-bold text-gray-800 mb-6">Create Service Alert</h1>

        <form onSubmit={handleSubmit} className="space-y-5">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Title *</label>
            <input
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              className="w-full border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="e.g. Signal failure at Market & Castro"
            />
            {errors.title && <p className="text-red-600 text-xs mt-1">{errors.title}</p>}
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Description</label>
            <textarea
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              rows={3}
              className="w-full border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 resize-none"
              placeholder="Describe the impact on service…"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Severity *</label>
            <select
              value={severity}
              onChange={(e) => setSeverity(e.target.value as AlertSeverity)}
              className="border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <option value="LOW">Low</option>
              <option value="MEDIUM">Medium</option>
              <option value="HIGH">High</option>
            </select>
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Affected Route *</label>
            <select
              value={routeId}
              onChange={(e) => setRouteId(e.target.value)}
              className="w-full border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <option value="">Select a route…</option>
              {routes?.map((r) => (
                <option key={r.id} value={r.id}>
                  {r.routeNumber} — {r.name}
                </option>
              ))}
            </select>
            {errors.routeId && <p className="text-red-600 text-xs mt-1">{errors.routeId}</p>}
          </div>

          {createMutation.isError && (
            <p className="text-red-600 text-sm">Failed to create alert. Please try again.</p>
          )}

          <div className="flex gap-3 pt-2">
            <button
              type="submit"
              disabled={createMutation.isPending}
              className="bg-blue-600 hover:bg-blue-700 text-white px-5 py-2 rounded-lg text-sm font-medium disabled:opacity-50 transition-colors"
            >
              {createMutation.isPending ? 'Creating…' : 'Create Alert'}
            </button>
            <Link
              to="/alerts"
              className="px-5 py-2 text-sm text-gray-600 hover:text-gray-800 font-medium"
            >
              Cancel
            </Link>
          </div>
        </form>
      </div>
    </div>
  )
}
