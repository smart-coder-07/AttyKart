module.exports = {
  content: ["./src/**/*.{html,ts}"],
  theme: {
    extend: {},
  },
  plugins: [
    require("tailwindcss"),
    require("autoprefixer"),
    require("daisyui"),
  ],
  daisyui: {
    themes: true, // Enable themes
    styled: true, // Enable component styling
    base: true, // Include base styles
    utils: true, // Include utility classes
    logs: true, // Display logs
    rtl: false, // Disable right-to-left support
  },
};
