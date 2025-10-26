let hourlyChart = null;

// Live clock
function updateClock() {
  const now = new Date();
  document.getElementById("clock").textContent = now.toLocaleTimeString([], {
    hour: "2-digit",
    minute: "2-digit",
    second: "2-digit",
  });
}
setInterval(updateClock, 1000);
updateClock();

// Weather icon
function getWeatherIcon(condition) {
  if (!condition) return "🌤️";
  const cond = condition.toLowerCase();
  if (cond.includes("clear") || cond.includes("sunny")) return "☀️";
  if (cond.includes("cloud")) return "☁️";
  if (cond.includes("rain")) return "🌧️";
  if (cond.includes("storm") || cond.includes("thunder")) return "⛈️";
  if (cond.includes("snow")) return "❄️";
  if (cond.includes("fog") || cond.includes("mist")) return "🌫️";
  return "🌤️";
}

// AQI color
function getAQIColor(aqi) {
  if (aqi <= 50) return "#00e400";
  if (aqi <= 100) return "#ffff00";
  if (aqi <= 150) return "#ff7e00";
  if (aqi <= 200) return "#ff0000";
  if (aqi <= 300) return "#99004c";
  return "#7e0023";
}

// Background based on condition
function updateBackground(condition) {
  const cond = (condition || "").toLowerCase();
  if (cond.includes("rain"))
    document.body.style.background = "linear-gradient(135deg, #3a506b, #5bc0be)";
  else if (cond.includes("cloud"))
    document.body.style.background = "linear-gradient(135deg, #90a4ae, #607d8b)";
  else
    document.body.style.background = "linear-gradient(135deg, #1c92d2, #f2fcfe)";
}

// SUN ARC position (perfect arc follow)
function updateSunPosition(sunrise, sunset) {
  const now = new Date();
  const sunriseTime = new Date(`1970-01-01T${sunrise}`);
  const sunsetTime = new Date(`1970-01-01T${sunset}`);
  const progress = Math.max(0, Math.min(1, (now - sunriseTime) / (sunsetTime - sunriseTime)));

  // Match SVG arc (10,90 -> 100,10 -> 190,90)
  const start = { x: 10, y: 90 };
  const control = { x: 100, y: 10 };
  const end = { x: 190, y: 90 };

  const svg = document.querySelector(".arc-path");
  const box = svg.getBoundingClientRect();

  const widthScale = box.width / 200;
  const heightScale = box.height / 100;

  const x =
    Math.pow(1 - progress, 2) * start.x +
    2 * (1 - progress) * progress * control.x +
    Math.pow(progress, 2) * end.x;

  const y =
    Math.pow(1 - progress, 2) * start.y +
    2 * (1 - progress) * progress * control.y +
    Math.pow(progress, 2) * end.y;

  const sun = document.getElementById("sunPosition");
  sun.style.left = `${x * widthScale + box.left + box.width / 6}px`;
  sun.style.top = `${y * heightScale + box.top / 8}px`;

  // Emoji and glow by time
  if (progress < 0.1) {
    sun.textContent = "🌅";
    sun.style.filter = "drop-shadow(0 0 8px rgba(255,160,80,0.8))";
  } else if (progress < 0.9) {
    sun.textContent = "☀️";
    sun.style.filter = "drop-shadow(0 0 10px rgba(255,255,150,1))";
  } else {
    sun.textContent = "🌇";
    sun.style.filter = "drop-shadow(0 0 8px rgba(255,90,60,0.9))";
  }

  document.getElementById("sunrise").textContent = `🌅 ${sunrise}`;
  document.getElementById("sunset").textContent = `${sunset} 🌆`;
}


// CHART.JS temperature graph
function createHourlyChart(hourlyData, condition) {
  const now = new Date();
  const upcoming = hourlyData.filter((h) => new Date(h.time) >= now);
  if (!upcoming.length) return;

  const ctx = document.getElementById("hourlyChart").getContext("2d");
  if (hourlyChart) hourlyChart.destroy();

  const labels = upcoming.map((h) =>
    new Date(h.time).toLocaleTimeString([], { hour: "numeric", hour12: true })
  );
  const temps = upcoming.map((h) => h.temp_c);

  let lineColor = "#FFD93D";
  if (condition.toLowerCase().includes("rain")) lineColor = "#00AAFF";
  else if (condition.toLowerCase().includes("cloud")) lineColor = "#CCCCCC";

  hourlyChart = new Chart(ctx, {
    type: "line",
    data: {
      labels,
      datasets: [
        {
          label: "Temperature (°C)",
          data: temps,
          borderColor: lineColor,
          backgroundColor: lineColor + "33",
          fill: true,
          tension: 0.4,
          borderWidth: 3,
          pointRadius: 5,
          pointBackgroundColor: lineColor,
          pointBorderColor: "#fff",
        },
      ],
    },
    options: {
      plugins: {
        legend: { display: false },
        datalabels: {
          align: "top",
          color: "#fff",
          font: { weight: "bold" },
          formatter: (v) => `${v}°`,
        },
      },
      scales: {
        x: {
          ticks: { color: "#fff" },
          grid: { color: "rgba(255,255,255,0.1)" },
        },
        y: {
          ticks: {
            color: "#fff",
            callback: (v) => v + "°",
          },
          grid: { color: "rgba(255,255,255,0.1)" },
        },
      },
      responsive: true,
      maintainAspectRatio: false,
    },
    plugins: [ChartDataLabels],
  });
}

// DAILY FORECAST table
function updateDailyForecast(days) {
  const tbody = document.getElementById("dailyBody");
  if (!tbody) return;
  tbody.innerHTML = "";
  days.forEach((d) => {
    tbody.innerHTML += `
      <tr>
        <td>${d.dayName}</td>
        <td>${getWeatherIcon(d.condition.text)}</td>
        <td>${Math.round(d.mintemp_c)}°C</td>
        <td>${Math.round(d.maxtemp_c)}°C</td>
        <td>${Math.round(d.avgtemp_c)}°C</td>
        <td>${d.condition.text}</td>
      </tr>`;
  });
}

// MAIN fetch
async function searchWeather() {
  const city = document.getElementById("cityInput").value.trim();
  if (!city) return alert("Enter a city name!");

  document.getElementById("loading").style.display = "block";
  document.getElementById("weatherContent").style.display = "none";

  try {
    const res = await fetch(`http://localhost:8080/weather/my/${encodeURIComponent(city)}`);
    if (!res.ok) throw new Error("City not found");
    const data = await res.json();

    document.getElementById("cityName").textContent = data.city;
    document.getElementById("temperature").textContent = Math.round(data.temperature) + "°";
    document.getElementById("condition").textContent = data.condition;
    document.getElementById("weatherIcon").textContent = getWeatherIcon(data.condition);
    document.getElementById("region").textContent = data.region || "";
    document.getElementById("country").textContent = data.country || "";

    const aqi = data.air_Quality_Index || 0;
    document.getElementById(
      "aqi"
    ).innerHTML = `${aqi} <span class="aqi-indicator" style="background:${getAQIColor(aqi)}"></span>`;

    updateBackground(data.condition);
    updateSunPosition(data.sunrise, data.sunset);

    if (data.hourlyForecast?.length) createHourlyChart(data.hourlyForecast, data.condition);
    if (data.dailySummaries?.length) updateDailyForecast(data.dailySummaries);

    // auto refresh sun position
    clearInterval(window.sunTimer);
    window.sunTimer = setInterval(() => updateSunPosition(data.sunrise, data.sunset), 60000);

    document.getElementById("loading").style.display = "none";
    document.getElementById("weatherContent").style.display = "block";
  } catch (e) {
    alert("Error: " + e.message);
    console.error(e);
    document.getElementById("loading").style.display = "none";
  }
}

// Default
window.addEventListener("DOMContentLoaded", () => {
  document.getElementById("cityInput").value = "Kasganj";
  searchWeather();
});
