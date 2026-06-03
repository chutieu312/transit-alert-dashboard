import '@testing-library/jest-dom'
import { vi } from 'vitest'

// Suppress console.error for expected error boundaries in tests
vi.spyOn(console, 'error').mockImplementation(() => {})
