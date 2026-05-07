import { defineConfig } from 'vite'
import reactSwc from '@vitejs/plugin-react-swc'

export default defineConfig({
  base: '/', plugins: [reactSwc()],
  server: {
    port: 5175,
    strictPort: false
  }
})
