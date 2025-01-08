  /** @type {import('tailwindcss').Config} */
export default {
  content: ["./src/main/resources/**/*.{html,js}",
    "./src/main/resources/templates/**/*.{html,js}",
    "./src/main/**/*.{html,js}",
  ],
  

  theme: {
    extend: {},
  },
  plugins: [ require('flowbite/plugin')],
  darkMode: "selector"

}


