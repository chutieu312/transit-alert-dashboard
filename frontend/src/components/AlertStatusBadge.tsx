import type { AlertStatus, AlertSeverity } from '@/types'

const STATUS_STYLES: Record<AlertStatus, string> = {
  ACTIVE: 'bg-red-100 text-red-800 border border-red-200',
  RESOLVED: 'bg-green-100 text-green-800 border border-green-200',
}

const SEVERITY_STYLES: Record<AlertSeverity, string> = {
  HIGH: 'bg-red-500 text-white',
  MEDIUM: 'bg-yellow-400 text-yellow-900',
  LOW: 'bg-blue-100 text-blue-800',
}

interface AlertStatusBadgeProps {
  status: AlertStatus
}

interface SeverityBadgeProps {
  severity: AlertSeverity
}

export function AlertStatusBadge({ status }: AlertStatusBadgeProps) {
  return (
    <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${STATUS_STYLES[status]}`}>
      {status}
    </span>
  )
}

export function SeverityBadge({ severity }: SeverityBadgeProps) {
  return (
    <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-bold ${SEVERITY_STYLES[severity]}`}>
      {severity}
    </span>
  )
}
