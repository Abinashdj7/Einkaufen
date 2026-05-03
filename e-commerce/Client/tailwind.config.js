/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          50: '#fdf4ff',
          500: '#a855f7',
          600: '#9333ea',
          700: '#7c3aed',
          800: '#6d28d9',
        },
        secondary: {
          50: '#fff7ed',
          500: '#f59e0b',
          600: '#d97706',
        },
        glass: 'rgba(255, 255, 255, 0.1)',
        dark: {
          900: '#111827',
          950: '#030712',
        }
      },
      fontFamily: {
        'sans': ['Inter', 'ui-sans-serif', 'system-ui'],
      },
      animation: {
        'fade-in': 'fadeIn 0.5s ease-in-out',
        'slide-up': 'slideUp 0.3s ease-out',
      },
      backdropBlur: {
        xs: '2px',
      }
    },
  },
  plugins: [
  ],
}