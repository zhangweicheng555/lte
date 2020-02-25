/*
 * L.Control.View a context menu for Leaflet.
 */
L.Control.View = L.Control.extend({
  options: {
    position: 'topleft' //初始位置
  },
  initialize: function (options) {
    L.Util.extend(this.options, options)
  },
  onAdd: function () {
    this._div = L.DomUtil.create('div', 'view')
    this.update('')
    return this._div
  },
  update: function (infos) {
    if (L.Util.isArray(infos)) {
      this._div.innerHTML = infos.join('')
    } else {
      this._div.innerHTML = infos
    }
    return this
  }
})
L.control.view = function (options) {
  return new L.Control.View(options)
}

/*
 * Leaflet.Control.Custom
 */
L.Control.Custom = L.Control.extend({
  version: '1.0.1',
  options: {
    position: 'topright',
    id: '',
    title: '',
    classes: '',
    content: '',
    style: {},
    datas: {},
    events: {},
  },
  container: null,
  onAdd: function (map) {
    this.container = L.DomUtil.create('div')
    this.container.id = this.options.id
    this.container.title = this.options.title
    this.container.className = this.options.classes
    this.container.innerHTML = this.options.content

    for (var option in this.options.style) {
      this.container.style[option] = this.options.style[option]
    }

    for (var data in this.options.datas) {
      this.container.dataset[data] = this.options.datas[data]
    }

    /* Prevent click events propagation to map */
    L.DomEvent.disableClickPropagation(this.container)

    /* Prevent right click event propagation to map */
    L.DomEvent.on(this.container, 'contextmenu', function (ev) {
      L.DomEvent.stopPropagation(ev)
    })

    /* Prevent scroll events propagation to map when cursor on the div */
    L.DomEvent.disableScrollPropagation(this.container)

    for (var event in this.options.events) {
      L.DomEvent.on(this.container, event, this.options.events[event], this.container)
    }

    return this.container
  },
  onRemove: function (map) {
    for (var event in this.options.events) {
      L.DomEvent.off(this.container, event, this.options.events[event], this.container)
    }
  }
})

L.control.custom = function custom (options) {
  return new L.Control.Custom(options)
}

/**L.Control.Home is a control to allow users show map i**/
/*L.Control.Home = L.Control.extend({
  options: {
    position: 'topleft',
    homeTitle: "Go to home map view"
  },
  onAdd: function(map) {
    if (!this.options.center) {
      this.options.center = map.getCenter();
    }
    if (!this.options.zoom) {
      this.options.zoom = map.getZoom();
    }
    options = this.options;
    // Create toolbar
    var controlName = 'leaflet-control-navbar',container;
    if(map.zoomControl){
      container = map.zoomControl._container;
    }else{
      container = L.DomUtil.create('div', controlName + ' leaflet-bar');
    }
    // Add toolbar buttons
    this._homeButton = this._createButton(options.homeTitle, controlName + '-home', container, this._goHome);
    // Set intial view to home
    map.setView(options.center, options.zoom);
    return container;
  },
  _goHome: function() {
    this._map.setView(this.options.center, this.options.zoom);
  },
  _createButton: function(title, className, container, fn) {
    // Modified from Leaflet zoom control
    var link = L.DomUtil.create('a', className, container);
    link.href = '#';
    link.title = title;
    L.DomEvent
      .on(link, 'mousedown dblclick', L.DomEvent.stopPropagation)
      .on(link, 'click', L.DomEvent.stop)
      .on(link, 'click', fn, this)
      .on(link, 'click', this._refocusOnMap, this);
    return link;
  }
});*/
/*L.Map.addInitHook(function() {
  if (this.options.homeControl) {
    this.addControl(new L.Control.Home());
  }
});
L.control.Home = function(options) {
  return new L.Control.Home(options);
};*/

var defaultPrecision = {
  km: 2,
  ha: 2,
  m: 0,
  mi: 2,
  ac: 2,
  yd: 0,
  ft: 0,
  nm: 2
}

/**
 * @class L.GeometryUtil
 * @aka GeometryUtil
 */
L.GeometryUtil = L.extend(L.GeometryUtil || {}, {
  // Ported from the OpenLayers implementation. See
  // https://github.com/openlayers/openlayers/blob/master/lib/OpenLayers/Geometry/LinearRing.js#L270

  // @method geodesicArea(): number
  geodesicArea: function (latLngs) {
    var pointsCount = latLngs.length,
      area = 0.0,
      d2r = Math.PI / 180,
      p1, p2

    if (pointsCount > 2) {
      for (var i = 0; i < pointsCount; i++) {
        p1 = latLngs[i]
        p2 = latLngs[(i + 1) % pointsCount]
        area += ((p2.lng - p1.lng) * d2r) *
          (2 + Math.sin(p1.lat * d2r) + Math.sin(p2.lat * d2r))
      }
      area = area * 6378137.0 * 6378137.0 / 2.0
    }

    return Math.abs(area)
  },

  // @method formattedNumber(n, precision): string
  // Returns n in specified number format (if defined) and precision
  formattedNumber: function (n, precision) {
    var formatted = n.toFixed(precision),
      format = L.drawLocal.format && L.drawLocal.format.numeric,
      delimiters = format && format.delimiters,
      thousands = delimiters && delimiters.thousands,
      decimal = delimiters && delimiters.decimal

    if (thousands || decimal) {
      var splitValue = formatted.split('.')
      formatted = thousands ? splitValue[0].replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1' + thousands) : splitValue[0]
      decimal = decimal || '.'
      if (splitValue.length > 1) {
        formatted = formatted + decimal + splitValue[1]
      }
    }

    return formatted
  },

  // @method readableArea(area, isMetric, precision): string
  // Returns a readable area string in yards or metric.
  // The value will be rounded as defined by the precision option object.
  readableArea: function (area, isMetric, precision) {
    var areaStr,
      units,
      precision = L.Util.extend({}, defaultPrecision, precision)

    if (isMetric) {
      units = ['ha', 'm']
      type = typeof isMetric
      if (type === 'string') {
        units = [isMetric]
      } else if (type !== 'boolean') {
        units = isMetric
      }

      if (area >= 1000000 && units.indexOf('km') !== -1) {
        areaStr = L.GeometryUtil.formattedNumber(area * 0.000001, precision['km']) + ' km²'
      } else if (area >= 10000 && units.indexOf('ha') !== -1) {
        areaStr = L.GeometryUtil.formattedNumber(area * 0.0001, precision['ha']) + ' ha'
      } else {
        areaStr = L.GeometryUtil.formattedNumber(area, precision['m']) + ' m²'
      }
    } else {
      area /= 0.836127 // Square yards in 1 meter

      if (area >= 3097600) { // 3097600 square yards in 1 square mile
        areaStr = L.GeometryUtil.formattedNumber(area / 3097600, precision['mi']) + ' mi²'
      } else if (area >= 4840) { // 4840 square yards in 1 acre
        areaStr = L.GeometryUtil.formattedNumber(area / 4840, precision['ac']) + ' acres'
      } else {
        areaStr = L.GeometryUtil.formattedNumber(area, precision['yd']) + ' yd²'
      }
    }

    return areaStr
  },

  // @method readableDistance(distance, units): string
  // Converts a metric distance to one of [ feet, nauticalMile, metric or
  // yards ] string
  //
  // @alternative
  // @method readableDistance(distance, isMetric, useFeet, isNauticalMile,
  // precision): string
  // Converts metric distance to distance string.
  // The value will be rounded as defined by the precision option object.
  readableDistance: function (distance, isMetric, isFeet, isNauticalMile, precision) {
    var distanceStr,
      units,
      precision = L.Util.extend({}, defaultPrecision, precision)

    if (isMetric) {
      units = typeof isMetric == 'string' ? isMetric : 'metric'
    } else if (isFeet) {
      units = 'feet'
    } else if (isNauticalMile) {
      units = 'nauticalMile'
    } else {
      units = 'yards'
    }

    switch (units) {
      case 'metric':
        // show metres when distance is < 1km, then show km
        if (distance > 1000) {
          distanceStr = L.GeometryUtil.formattedNumber(distance / 1000, precision['km']) + ' km'
        } else {
          distanceStr = L.GeometryUtil.formattedNumber(distance, precision['m']) + ' m'
        }
        break
      case 'feet':
        distance *= 1.09361 * 3
        distanceStr = L.GeometryUtil.formattedNumber(distance, precision['ft']) + ' ft'

        break
      case 'nauticalMile':
        distance *= 0.53996
        distanceStr = L.GeometryUtil.formattedNumber(distance / 1000, precision['nm']) + ' nm'
        break
      case 'yards':
      default:
        distance *= 1.09361

        if (distance > 1760) {
          distanceStr = L.GeometryUtil.formattedNumber(distance / 1760, precision['mi']) + ' miles'
        } else {
          distanceStr = L.GeometryUtil.formattedNumber(distance, precision['yd']) + ' yd'
        }
        break
    }
    return distanceStr
  }
})

// export {
//   custom
// }
