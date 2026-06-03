import type { Meta, StoryObj } from '@storybook/react'
import { AlertStatusBadge, SeverityBadge } from '@/components/AlertStatusBadge'

const meta: Meta = {
  title: 'Components/AlertStatusBadge',
  tags: ['autodocs'],
}
export default meta

export const StatusActive: StoryObj = {
  render: () => <AlertStatusBadge status="ACTIVE" />,
}

export const StatusResolved: StoryObj = {
  render: () => <AlertStatusBadge status="RESOLVED" />,
}

export const SeverityHigh: StoryObj = {
  render: () => <SeverityBadge severity="HIGH" />,
}

export const SeverityMedium: StoryObj = {
  render: () => <SeverityBadge severity="MEDIUM" />,
}

export const SeverityLow: StoryObj = {
  render: () => <SeverityBadge severity="LOW" />,
}
