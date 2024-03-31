/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './src/**/*.{js,jsx,ts,tsx}',
  ],
  theme: {
    extend: {
      colors: {
        'light-gray': '#dcdcdd',
        'off-white': '#FAF9F6',
        'darker-white': '#F5F3EF',
        'primary': '#8162b4',
        'secondary': '#d87860',
      },
    },
  },
  plugins: [],
}

