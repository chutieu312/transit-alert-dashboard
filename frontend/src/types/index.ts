// TypeScript interfaces matching the Spring Boot API models

export type AlertStatus = 'ACTIVE' | 'RESOLVED'
export type AlertSeverity = 'LOW' | 'MEDIUM' | 'HIGH'

export interface Route {
  id: string
  routeNumber: string
  name: string
  description?: string
  type: string
  active: boolean
  createdAt: string
  updatedAt: string
}

export interface Stop {
  id: string
  routeId: string
  stopCode: string
  name: string
  latitude: number
  longitude: number
  sequenceOrder: number
  createdAt: string
}

export interface Alert {
  id: string
  title: string
  description?: string
  severity: AlertSeverity
  status: AlertStatus
  affectedRouteId: string
  affectedRouteName: string
  createdByEmail: string
  createdAt: string
  resolvedAt?: string
}

export interface Page<T> {
  content: T[]
  totalElements: number
  totalPages: number
  number: number
  size: number
}

export interface AlertSummary {
  ACTIVE: number
  RESOLVED: number
}

export interface AlertRequest {
  title: string
  description?: string
  severity: AlertSeverity
  affectedRouteId: string
}

export interface AuthState {
  token: string | null
  fullName: string | null
  role: string | null
}
