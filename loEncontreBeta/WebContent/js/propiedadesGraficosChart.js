var MONTHS = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Augosto", "Septembre", "Octubre", "Noviembre", "Diciembre"];

var randomScalingFactor = function() {
	return Math.round(Math.random() * 50 * (Math.random() > 0.5 ? 1 : 1)) + 50;
};
var randomColorFactor = function() {
	return Math.round(Math.random() * 255);
};
var randomColor = function(opacity) {
	return 'rgba(' + randomColorFactor() + ',' + randomColorFactor() + ',' + randomColorFactor() + ',' + (opacity || '.3') + ')';
};
var hola = 0;
var config = {
		type: 'line',
		data: {
			labels: ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio"],
			datasets: [{
				label: "Se registron",
				data: [5,10, 99, 0,100, 50],
				fill: false,
				borderDash: [20, 10],
			}]
		},
		options: {
			responsive: true,
			title:{
				display:true,
				text:"Documentos Registrados Durante el año 2016 hasta el mes actual"
			},
			scales: {
				xAxes: [{
					display: true,
					ticks: {
						userCallback: function(dataLabel, index) {
							return index % 2 === 0 ? dataLabel : '';
						}
					}
				}],
				yAxes: [{
					display: true,
					beginAtZero: false
				}]
			}
		}
};


var configClient = {
		type: 'line',
		data: {
			labels: ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio"],
			datasets: [{
				label: "Documentos que has encontrado",
				data: [30,20,40,10,0,0],
				fill: false,
				borderDash: [20, 10],
			}]
		},
		options: {
			responsive: true,
			title:{
				display:true,
				text:"Tus Estadisticas"
			},
			scales: {
				xAxes: [{
					display: true,
					ticks: {
						userCallback: function(dataLabel, index) {
							return index % 2 === 0 ? dataLabel : '';
						}
					}
				}],
				yAxes: [{
					display: true,
					beginAtZero: false
				}]
			}
		}
};


$.each(config.data.datasets, function(i, dataset) {
	dataset.borderColor = randomColor(0.4);
	dataset.backgroundColor = randomColor(0.5);
	dataset.pointBorderColor = randomColor(0.7);
	dataset.pointBackgroundColor = randomColor(0.5);
	dataset.pointBorderWidth = 1;
});

$.each(configClient.data.datasets, function(i, dataset) {
	dataset.borderColor = randomColor(0.4);
	dataset.backgroundColor = randomColor(0.5);
	dataset.pointBorderColor = randomColor(0.7);
	dataset.pointBackgroundColor = randomColor(0.5);
	dataset.pointBorderWidth = 1;
});

function loadGraphics(){
	var ctx = document.getElementById("canvas").getContext("2d");
	window.myLine = new Chart(ctx, config);
	var ctxCliente = document.getElementById("canvasEstatisticasCliente").getContext("2d");
	window.myLine = new Chart(ctxCliente, configClient);
};


$('#addData').click(function() {
	if (config.data.datasets.length > 0) {
		var month = MONTHS[config.data.labels.length % MONTHS.length];
		config.data.labels.push(month);

		for (var index = 0; index < config.data.datasets.length; ++index) {
			config.data.datasets[index].data.push(randomScalingFactor());
		}
		window.myLine.update();
	}
});
