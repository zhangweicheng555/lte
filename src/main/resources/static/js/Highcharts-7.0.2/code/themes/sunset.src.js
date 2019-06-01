/**
 * @license Highcharts JS v7.0.2 (2019-01-17)
 *
 * (c) 2009-2019 Highsoft AS
 *
 * License: www.highcharts.com/license
 */
'use strict';
(function (factory) {
	if (typeof module === 'object' && module.exports) {
		factory['default'] = factory;
		module.exports = factory;
	} else if (typeof define === 'function' && define.amd) {
		define(function () {
			return factory;
		});
	} else {
		factory(typeof Highcharts !== 'undefined' ? Highcharts : undefined);
	}
}(function (Highcharts) {
	(function (Highcharts) {
		/**
		 * (c) 2010-2017 Highsoft AS
		 *
		 * License: www.highcharts.com/license
		 *
		 * Accessible high-contrast theme for Highcharts. Considers colorblindness and
		 * monochrome rendering.
		 * @author Øystein Moseng
		 */

		Highcharts.theme = {
		    colors: ['#FDD089', '#FF7F79', '#A0446E', '#251535'],

		    colorAxis: {
		        maxColor: '#60042E',
		        minColor: '#FDD089'
		    },

		    plotOptions: {
		        map: {
		            nullColor: '#fefefc'
		        }
		    },

		    navigator: {
		        series: {
		            color: '#FF7F79',
		            lineColor: '#A0446E'
		        }
		    }
		};

		// Apply the theme
		Highcharts.setOptions(Highcharts.theme);

	}(Highcharts));
	return (function () {


	}());
}));
