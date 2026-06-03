import api from './client'
import type { Alert, AlertRequest, AlertStatus, AlertSummary, Page, Route, Stop } from '@/types'

export const alertsApi = {
  list: (params: {
    status?: AlertStatus
    routeId?: string
    page?: number
    size?: number
  }) => api.get<Page<Alert>>('/alerts', { params }).then((r) => r.data),

  summary: () => api.get<AlertSummary>('/alerts/summary').then((r) => r.data),

  getById: (id: string) => api.get<Alert>(`/alerts/${id}`).then((r) => r.data),

  create: (req: AlertRequest) => api.post<Alert>('/alerts', req).then((r) => r.data),

  resolve: (id: string) => api.patch<Alert>(`/alerts/${id}/resolve`).then((r) => r.data),

  delete: (id: string) => api.delete(`/alerts/${id}`),
}

export const routesApi = {
  list: (params?: { page?: number; size?: number }) =>
    api.get<Page<Route>>('/routes', { params }).then((r) => r.data),

  listActive: () => api.get<Route[]>('/routes/active').then((r) => r.data),

  getById: (id: string) => api.get<Route>(`/routes/${id}`).then((r) => r.data),

  stopCount: (routeId: string) =>
    api.get<number>(`/routes/${routeId}/stops/count`).then((r) => r.data),

  stops: (routeId: string) =>
    api.get<Stop[]>(`/routes/${routeId}/stops`).then((r) => r.data),
}

export const authApi = {
  login: (email: string, password: string) =>
    api
      .post<{ token: string; fullName: string; role: string }>('/auth/login', { email, password })
      .then((r) => r.data),
}
