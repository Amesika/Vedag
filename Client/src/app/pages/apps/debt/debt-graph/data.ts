import { ChartType } from "./debt-graph.model";

function getDaysInMonth(month, year) {
  const date = new Date(year, month, 1);
  const days = [];
  let idx = 0;
  while (date.getMonth() === month && idx < 15) {
    const d = new Date(date);
    days.push(d.getDate() + ' ' + d.toLocaleString('en-us', { month: 'short' }));
    date.setDate(date.getDate() + 1);
    idx += 1;
  }
  return days;
}

const now = new Date();
const labels = getDaysInMonth(now.getMonth(), now.getFullYear());
const revenueAreaChart: ChartType = {
  series: [
    {
      name: "PRODUCT A",
      data: [44, 55, 41, 67, 22, 43]
    }
  ],
  chart: {
    type: "bar",
    height: 350,
    stacked: true,
    toolbar: {
      show: true
    },
    zoom: {
      enabled: true
    }
  },
  responsive: [
    {
      breakpoint: 480,
      options: {
        legend: {
          position: "bottom",
          offsetX: -10,
          offsetY: 0
        }
      }
    }
  ],
  plotOptions: {
    bar: {
      horizontal: false
    }
  },
  xaxis: {
    type: "category",
    categories: []
  },
  legend: {
    position: "right",
    offsetY: 40
  },
  fill: {
    opacity: 1
  }
};

export { revenueAreaChart };
