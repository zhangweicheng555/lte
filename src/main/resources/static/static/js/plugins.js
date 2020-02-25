/*
 * heatmap.js v2.0.5 | JavaScript Heatmap Library
 *
 * Copyright 2008-2016 Patrick Wied <heatmapjs@patrick-wied.at> - All rights reserved.
 * Dual licensed under MIT and Beerware license
 *
 * :: 2016-09-05 01:16
 */
(function (a, b, c) {
  if (typeof module !== "undefined" && module.exports) {
    module.exports = c()
  } else if (typeof define === "function" && define.amd) {
    define(c)
  } else {
    b[a] = c()
  }
})("h337", this, function () {
  var a = {
    defaultRadius: 40,
    defaultRenderer: "canvas2d",
    defaultGradient: {.25: "rgb(0,0,255)", .55: "rgb(0,255,0)", .85: "yellow", 1: "rgb(255,0,0)"},
    defaultMaxOpacity: 1,
    defaultMinOpacity: 0,
    defaultBlur: .85,
    defaultXField: "x",
    defaultYField: "y",
    defaultValueField: "value",
    plugins: {}
  };
  var b = function h() {
    var b = function d(a) {
      this._coordinator = {};
      this._data = [];
      this._radi = [];
      this._min = 10;
      this._max = 1;
      this._xField = a["xField"] || a.defaultXField;
      this._yField = a["yField"] || a.defaultYField;
      this._valueField = a["valueField"] || a.defaultValueField;
      if (a["radius"]) {
        this._cfgRadius = a["radius"]
      }
    };
    var c = a.defaultRadius;
    b.prototype = {
      _organiseData: function (a, b) {
        var d = a[this._xField];
        var e = a[this._yField];
        var f = this._radi;
        var g = this._data;
        var h = this._max;
        var i = this._min;
        var j = a[this._valueField] || 1;
        var k = a.radius || this._cfgRadius || c;
        if (!g[d]) {
          g[d] = [];
          f[d] = []
        }
        if (!g[d][e]) {
          g[d][e] = j;
          f[d][e] = k
        } else {
          g[d][e] += j
        }
        var l = g[d][e];
        if (l > h) {
          if (!b) {
            this._max = l
          } else {
            this.setDataMax(l)
          }
          return false
        } else if (l < i) {
          if (!b) {
            this._min = l
          } else {
            this.setDataMin(l)
          }
          return false
        } else {
          return {x: d, y: e, value: j, radius: k, min: i, max: h}
        }
      }, _unOrganizeData: function () {
        var a = [];
        var b = this._data;
        var c = this._radi;
        for (var d in b) {
          for (var e in b[d]) {
            a.push({x: d, y: e, radius: c[d][e], value: b[d][e]})
          }
        }
        return {min: this._min, max: this._max, data: a}
      }, _onExtremaChange: function () {
        this._coordinator.emit("extremachange", {min: this._min, max: this._max})
      }, addData: function () {
        if (arguments[0].length > 0) {
          var a = arguments[0];
          var b = a.length;
          while (b--) {
            this.addData.call(this, a[b])
          }
        } else {
          var c = this._organiseData(arguments[0], true);
          if (c) {
            if (this._data.length === 0) {
              this._min = this._max = c.value
            }
            this._coordinator.emit("renderpartial", {min: this._min, max: this._max, data: [c]})
          }
        }
        return this
      }, setData: function (a) {
        var b = a.data;
        var c = b.length;
        this._data = [];
        this._radi = [];
        for (var d = 0; d < c; d++) {
          this._organiseData(b[d], false)
        }
        this._max = a.max;
        this._min = a.min || 0;
        this._onExtremaChange();
        this._coordinator.emit("renderall", this._getInternalData());
        return this
      }, removeData: function () {
      }, setDataMax: function (a) {
        this._max = a;
        this._onExtremaChange();
        this._coordinator.emit("renderall", this._getInternalData());
        return this
      }, setDataMin: function (a) {
        this._min = a;
        this._onExtremaChange();
        this._coordinator.emit("renderall", this._getInternalData());
        return this
      }, setCoordinator: function (a) {
        this._coordinator = a
      }, _getInternalData: function () {
        return {max: this._max, min: this._min, data: this._data, radi: this._radi}
      }, getData: function () {
        return this._unOrganizeData()
      }
    };
    return b
  }();
  var c = function i() {
    var a = function (a) {
      var b = a.gradient || a.defaultGradient;
      var c = document.createElement("canvas");
      var d = c.getContext("2d");
      c.width = 256;
      c.height = 1;
      var e = d.createLinearGradient(0, 0, 256, 1);
      for (var f in b) {
        e.addColorStop(f, b[f])
      }
      d.fillStyle = e;
      d.fillRect(0, 0, 256, 1);
      return d.getImageData(0, 0, 256, 1).data
    };
    var b = function (a, b) {
      var c = document.createElement("canvas");
      var d = c.getContext("2d");
      var e = a;
      var f = a;
      c.width = c.height = a * 2;
      if (b == 1) {
        d.beginPath();
        d.arc(e, f, a, 0, 2 * Math.PI, false);
        d.fillStyle = "rgba(0,0,0,1)";
        d.fill()
      } else {
        var g = d.createRadialGradient(e, f, a * b, e, f, a);
        g.addColorStop(0, "rgba(0,0,0,1)");
        g.addColorStop(1, "rgba(0,0,0,0)");
        d.fillStyle = g;
        d.fillRect(0, 0, 2 * a, 2 * a)
      }
      return c
    };
    var c = function (a) {
      var b = [];
      var c = a.min;
      var d = a.max;
      var e = a.radi;
      var a = a.data;
      var f = Object.keys(a);
      var g = f.length;
      while (g--) {
        var h = f[g];
        var i = Object.keys(a[h]);
        var j = i.length;
        while (j--) {
          var k = i[j];
          var l = a[h][k];
          var m = e[h][k];
          b.push({x: h, y: k, value: l, radius: m})
        }
      }
      return {min: c, max: d, data: b}
    };

    function d(b) {
      var c = b.container;
      var d = this.shadowCanvas = document.createElement("canvas");
      var e = this.canvas = b.canvas || document.createElement("canvas");
      var f = this._renderBoundaries = [1e4, 1e4, 0, 0];
      var g = getComputedStyle(b.container) || {};
      e.className = "heatmap-canvas";
      this._width = e.width = d.width = b.width || +g.width.replace(/px/, "");
      this._height = e.height = d.height = b.height || +g.height.replace(/px/, "");
      this.shadowCtx = d.getContext("2d");
      this.ctx = e.getContext("2d");
      e.style.cssText = d.style.cssText = "position:absolute;left:0;top:0;";
      c.style.position = "relative";
      c.appendChild(e);
      this._palette = a(b);
      this._templates = {};
      this._setStyles(b)
    }

    d.prototype = {
      renderPartial: function (a) {
        if (a.data.length > 0) {
          this._drawAlpha(a);
          this._colorize()
        }
      }, renderAll: function (a) {
        this._clear();
        if (a.data.length > 0) {
          this._drawAlpha(c(a));
          this._colorize()
        }
      }, _updateGradient: function (b) {
        this._palette = a(b)
      }, updateConfig: function (a) {
        if (a["gradient"]) {
          this._updateGradient(a)
        }
        this._setStyles(a)
      }, setDimensions: function (a, b) {
        this._width = a;
        this._height = b;
        this.canvas.width = this.shadowCanvas.width = a;
        this.canvas.height = this.shadowCanvas.height = b
      }, _clear: function () {
        this.shadowCtx.clearRect(0, 0, this._width, this._height);
        this.ctx.clearRect(0, 0, this._width, this._height)
      }, _setStyles: function (a) {
        this._blur = a.blur == 0 ? 0 : a.blur || a.defaultBlur;
        if (a.backgroundColor) {
          this.canvas.style.backgroundColor = a.backgroundColor
        }
        this._width = this.canvas.width = this.shadowCanvas.width = a.width || this._width;
        this._height = this.canvas.height = this.shadowCanvas.height = a.height || this._height;
        this._opacity = (a.opacity || 0) * 255;
        this._maxOpacity = (a.maxOpacity || a.defaultMaxOpacity) * 255;
        this._minOpacity = (a.minOpacity || a.defaultMinOpacity) * 255;
        this._useGradientOpacity = !!a.useGradientOpacity
      }, _drawAlpha: function (a) {
        var c = this._min = a.min;
        var d = this._max = a.max;
        var a = a.data || [];
        var e = a.length;
        var f = 1 - this._blur;
        while (e--) {
          var g = a[e];
          var h = g.x;
          var i = g.y;
          var j = g.radius;
          var k = Math.min(g.value, d);
          var l = h - j;
          var m = i - j;
          var n = this.shadowCtx;
          var o;
          if (!this._templates[j]) {
            this._templates[j] = o = b(j, f)
          } else {
            o = this._templates[j]
          }
          var p = (k - c) / (d - c);
          n.globalAlpha = p < .01 ? .01 : p;
          n.drawImage(o, l, m);
          if (l < this._renderBoundaries[0]) {
            this._renderBoundaries[0] = l
          }
          if (m < this._renderBoundaries[1]) {
            this._renderBoundaries[1] = m
          }
          if (l + 2 * j > this._renderBoundaries[2]) {
            this._renderBoundaries[2] = l + 2 * j
          }
          if (m + 2 * j > this._renderBoundaries[3]) {
            this._renderBoundaries[3] = m + 2 * j
          }
        }
      }, _colorize: function () {
        var a = this._renderBoundaries[0];
        var b = this._renderBoundaries[1];
        var c = this._renderBoundaries[2] - a;
        var d = this._renderBoundaries[3] - b;
        var e = this._width;
        var f = this._height;
        var g = this._opacity;
        var h = this._maxOpacity;
        var i = this._minOpacity;
        var j = this._useGradientOpacity;
        if (a < 0) {
          a = 0
        }
        if (b < 0) {
          b = 0
        }
        if (a + c > e) {
          c = e - a
        }
        if (b + d > f) {
          d = f - b
        }
        var k = this.shadowCtx.getImageData(a, b, c, d);
        var l = k.data;
        var m = l.length;
        var n = this._palette;
        for (var o = 3; o < m; o += 4) {
          var p = l[o];
          var q = p * 4;
          if (!q) {
            continue
          }
          var r;
          if (g > 0) {
            r = g
          } else {
            if (p < h) {
              if (p < i) {
                r = i
              } else {
                r = p
              }
            } else {
              r = h
            }
          }
          l[o - 3] = n[q];
          l[o - 2] = n[q + 1];
          l[o - 1] = n[q + 2];
          l[o] = j ? n[q + 3] : r
        }
        k.data = l;
        this.ctx.putImageData(k, a, b);
        this._renderBoundaries = [1e3, 1e3, 0, 0]
      }, getValueAt: function (a) {
        var b;
        var c = this.shadowCtx;
        var d = c.getImageData(a.x, a.y, 1, 1);
        var e = d.data[3];
        var f = this._max;
        var g = this._min;
        b = Math.abs(f - g) * (e / 255) >> 0;
        return b
      }, getDataURL: function () {
        return this.canvas.toDataURL()
      }
    };
    return d
  }();
  var d = function j() {
    var b = false;
    if (a["defaultRenderer"] === "canvas2d") {
      b = c
    }
    return b
  }();
  var e = {
    merge: function () {
      var a = {};
      var b = arguments.length;
      for (var c = 0; c < b; c++) {
        var d = arguments[c];
        for (var e in d) {
          a[e] = d[e]
        }
      }
      return a
    }
  };
  var f = function k() {
    var c = function h() {
      function a() {
        this.cStore = {}
      }

      a.prototype = {
        on: function (a, b, c) {
          var d = this.cStore;
          if (!d[a]) {
            d[a] = []
          }
          d[a].push(function (a) {
            return b.call(c, a)
          })
        }, emit: function (a, b) {
          var c = this.cStore;
          if (c[a]) {
            var d = c[a].length;
            for (var e = 0; e < d; e++) {
              var f = c[a][e];
              f(b)
            }
          }
        }
      };
      return a
    }();
    var f = function (a) {
      var b = a._renderer;
      var c = a._coordinator;
      var d = a._store;
      c.on("renderpartial", b.renderPartial, b);
      c.on("renderall", b.renderAll, b);
      c.on("extremachange", function (b) {
        a._config.onExtremaChange && a._config.onExtremaChange({
          min: b.min,
          max: b.max,
          gradient: a._config["gradient"] || a._config["defaultGradient"]
        })
      });
      d.setCoordinator(c)
    };

    function g() {
      var g = this._config = e.merge(a, arguments[0] || {});
      this._coordinator = new c;
      if (g["plugin"]) {
        var h = g["plugin"];
        if (!a.plugins[h]) {
          throw new Error("Plugin '" + h + "' not found. Maybe it was not registered.")
        } else {
          var i = a.plugins[h];
          this._renderer = new i.renderer(g);
          this._store = new i.store(g)
        }
      } else {
        this._renderer = new d(g);
        this._store = new b(g)
      }
      f(this)
    }

    g.prototype = {
      addData: function () {
        this._store.addData.apply(this._store, arguments);
        return this
      }, removeData: function () {
        this._store.removeData && this._store.removeData.apply(this._store, arguments);
        return this
      }, setData: function () {
        this._store.setData.apply(this._store, arguments);
        return this
      }, setDataMax: function () {
        this._store.setDataMax.apply(this._store, arguments);
        return this
      }, setDataMin: function () {
        this._store.setDataMin.apply(this._store, arguments);
        return this
      }, configure: function (a) {
        this._config = e.merge(this._config, a);
        this._renderer.updateConfig(this._config);
        this._coordinator.emit("renderall", this._store._getInternalData());
        return this
      }, repaint: function () {
        this._coordinator.emit("renderall", this._store._getInternalData());
        return this
      }, getData: function () {
        return this._store.getData()
      }, getDataURL: function () {
        return this._renderer.getDataURL()
      }, getValueAt: function (a) {
        if (this._store.getValueAt) {
          return this._store.getValueAt(a)
        } else if (this._renderer.getValueAt) {
          return this._renderer.getValueAt(a)
        } else {
          return null
        }
      }
    };
    return g
  }();
  var g = {
    create: function (a) {
      return new f(a)
    }, register: function (b, c) {
      a.plugins[b] = c
    }
  };
  return g
});
/**
 * echarts leaflet地图扩展，必须在echarts初始化前使用
 * @desc echarts基于Canvas，纯Javascript图表库，提供直观，生动，可交互，可个性化定制的数据统计图表。
 * @author EricLee
 */
L.EchartsOverlay = L.Layer.extend({
  /**
   * 初始化方法
   *
   * @param {echarts} ec
   * @private
   */
  initialize: function (ec, opts) {
    this._el = L.DomUtil.create('div', 'leaflet-zoom-hide');
    this.ec = ec;
    this._geoCoord = [];
    L.setOptions(this, opts);
  },
  /**
   * 初始化echarts实例
   *
   * @return {ECharts}
   * @public
   */
  initECharts: function () {
    // ec.init.apply(self, arguments);
    this._ec = this.ec.init.apply(this, arguments);
    this._bindEvent();
    this._addMarkWrap();
    return this._ec;
  },
  getEvents: function () {
    return {
      resize: this._resize,
      moveend: this.refresh,
      viewreset: this._reset
    };
  },
  onAdd: function (map) {
    this._map = map;
    var size = map.getSize();
    this._el.style.height = size.y + 'px';
    this._el.style.width = size.x + 'px';
    this._reset();
    map._panes.overlayPane.appendChild(this._el);
    this.refresh();
  },

  onRemove: function (map) {
    this._map._panes.overlayPane.removeChild(this._el);
  },
  addTo: function (map) {
    map.addLayer(this);
    return this;
  },
  /**
   * 刷新页面
   *
   * @public
   */
  refresh: function () {
    if (this._ec) {
      var point = this._map.getPanes().mapPane._leaflet_pos;
      L.DomUtil.setPosition(this._el, new L.Point(-Math.round(point.x), -Math.round(point.y)));
      this._el.style.position = 'absolute';
      //var option = this._ec.getOption();
      var option = this.option;
      if (option) {
        this._ec.clear();
        this.setOption(option, false);
      }
    }
  },
  getContainer: function () {
    return this._el;
  },

  getMap: function () {
    return this._map;
  },
  /**
   * 获取ECharts实例
   *
   * @return {ECharts}
   * @public
   */
  getECharts: function () {
    return this._ec;
  },

  /**
   * 对echarts的setOption加一次处理
   * 用来为markPoint、markLine中添加x、y坐标，需要name与geoCoord对应
   *
   * @param {Object}
   * @public
   */
  setOption: function (option, notMerge) {
    if (notMerge) {
      this.option = option;
    }
    var series = option.series || {};
    // 记录所有的geoCoord
    for (var i = 0, item; item = series[i++];) {
      var geoCoord = item.geoCoord;
      if (geoCoord) {
        for (var k in geoCoord) {
          this._geoCoord[k] = geoCoord[k];
        }
      }
    }
    // 添加x、y
    for (var i = 0, item; item = series[i++];) {
      var markPoint = item.markPoint || {};
      var markLine = item.markLine || {};

      var data = markPoint.data;
      if (data && data.length) {
        for (var k = 0; k < data.length; k++) {
          this._AddPos(data[k]);
        }
      }

      data = markLine.data;
      if (data && data.length) {
        for (var k = 0; k < data.length; k++) {
          this._AddPos(data[k][0]);
          this._AddPos(data[k][1]);
        }
      }
    }
    this._ec.setOption(option, notMerge);
  },

  /**
   * 经纬度转换为屏幕像素
   *
   * @param {Array.<number>} geoCoord  经纬度
   * @return Point
   * @public
   */
  geoCoord2Pixel: function (geoCoord) {
    return this._map.latLngToContainerPoint([geoCoord[1], geoCoord[0]]);
  },
  _addMarkWrap: function () {
    var me = this;

    function _addMark(seriesIdx, markData, markType) {
      var data;
      if (markType == 'markPoint') {
        var data = markData.data;
        if (data && data.length) {
          for (var k = 0; k < data.length; k++) {
            me._AddPos(data[k]);
          }
        }
      } else {
        data = markData.data;
        if (data && data.length) {
          for (var k = 0; k < data.length; k++) {
            me._AddPos(data[k][0]);
            me._AddPos(data[k][1]);
          }
        }
      }
      me._ec._addMarkOri(seriesIdx, markData, markType);
    }

    me._ec._addMarkOri = me._ec._addMark;
    me._ec._addMark = _addMark;
  },

  /**
   * 增加x、y坐标
   *
   * @param {Object} obj  markPoint、markLine data中的项，必须有name
   * @param {Object} geoCoord
   */
  _AddPos: function (obj) {
    var coord = this._geoCoord[obj.name]
    var pos = this.geoCoord2Pixel(coord);
    obj.x = pos.x;
    obj.y = pos.y;
  },
  /**
   * 绑定地图事件的处理方法
   *
   * @private
   */
  _bindEvent: function () {
    var me = this;
    this._ec.getZrender().on('mouseover', function () {
      me._map.dragging.disable();
    });
    this._ec.getZrender().on('mouseout', function () {
      me._map.dragging.enable();
    });
  },

  _reset: function () {
    if (!this._map) {
      return;
    }
    this._origin = this._map.layerPointToLatLng(new L.Point(0, 0));
    var size = this._map.getSize();
    if (this._width !== size.x || this._height !== size.y) {
      this._width = size.x;
      this._height = size.y;

      this._el.style.width = this._width + 'px';
      this._el.style.height = this._height + 'px';
      if (this._ec) {
        this._ec.resize();
      }
    }
    this.refresh();
  },

  /**
   * Zrender拖拽触发事件
   *
   * @param {boolean} isStart
   * @return {Function}
   * @private
   */
  _dragZrenderHandler: function (isStart) {
    var me = this;
    if (isStart) {
      me._map.dragging.disable();
    } else {
      me._map.dragging.enable();
    }
  },
  _resize: function (e) {
    var size = this._map.getSize();
    if (this._width !== size.x || this._height !== size.y) {
      this._width = size.x;
      this._height = size.y;

      this._el.style.width = this._width + 'px';
      this._el.style.height = this._height + 'px';

      if (this._ec) {
        this._ec.resize();
      }
    }
  }
});

L.echartsOverlay = function (ec, opts) {
  return new L.EchartsOverlay(ec, opts);
};
L.Control.Fullscreen = L.Control.extend({
  options: {
    position: 'topleft',
    title: {
      'false': 'View Fullscreen',
      'true': 'Exit Fullscreen'
    }
  },

  onAdd: function (map) {
    var container;
    if (map.zoomControl) {
      container = map.zoomControl._container;
    } else {
      container = L.DomUtil.create('div', 'leaflet-bar leaflet-control');
    }
    this.link = L.DomUtil.create('a', 'leaflet-control-fullscreen leaflet-bar-part', container);
    this.link.href = '#';
    L.DomUtil.addClass(container, 'leaflet-fullscreen');
    this._map = map;
    this._map.on('fullscreenchange', this._toggleTitle, this);
    this._toggleTitle();

    L.DomEvent.on(this.link, 'click', this._click, this);

    return container;
  },

  _click: function (e) {
    L.DomEvent.stopPropagation(e);
    L.DomEvent.preventDefault(e);
    this._map.toggleFullscreen(this.options);
  },

  _toggleTitle: function () {
    this.link.title = this.options.title[this._map.isFullscreen()];
  }
});

L.Map.include({
  isFullscreen: function () {
    return this._isFullscreen || false;
  },

  toggleFullscreen: function (options) {
    var container = this.getContainer();
    if (this.isFullscreen()) {
      if (options && options.pseudoFullscreen) {
        this._disablePseudoFullscreen(container);
      } else if (document.exitFullscreen) {
        document.exitFullscreen();
      } else if (document.mozCancelFullScreen) {
        document.mozCancelFullScreen();
      } else if (document.webkitCancelFullScreen) {
        document.webkitCancelFullScreen();
      } else if (document.msExitFullscreen) {
        document.msExitFullscreen();
      } else {
        this._disablePseudoFullscreen(container);
      }
    } else {
      if (options && options.pseudoFullscreen) {
        this._enablePseudoFullscreen(container);
      } else if (container.requestFullscreen) {
        container.requestFullscreen();
      } else if (container.mozRequestFullScreen) {
        container.mozRequestFullScreen();
      } else if (container.webkitRequestFullscreen) {
        container.webkitRequestFullscreen(Element.ALLOW_KEYBOARD_INPUT);
      } else if (container.msRequestFullscreen) {
        container.msRequestFullscreen();
      } else {
        this._enablePseudoFullscreen(container);
      }
    }

  },

  _enablePseudoFullscreen: function (container) {
    L.DomUtil.addClass(container, 'leaflet-pseudo-fullscreen');
    this._setFullscreen(true);
    this.fire('fullscreenchange');
  },

  _disablePseudoFullscreen: function (container) {
    L.DomUtil.removeClass(container, 'leaflet-pseudo-fullscreen');
    this._setFullscreen(false);
    this.fire('fullscreenchange');
  },

  _setFullscreen: function (fullscreen) {
    this._isFullscreen = fullscreen;
    var container = this.getContainer();
    if (fullscreen) {
      L.DomUtil.addClass(container, 'leaflet-fullscreen-on');
    } else {
      L.DomUtil.removeClass(container, 'leaflet-fullscreen-on');
    }
    this.invalidateSize();
  },

  _onFullscreenChange: function (e) {
    var fullscreenElement =
      document.fullscreenElement ||
      document.mozFullScreenElement ||
      document.webkitFullscreenElement ||
      document.msFullscreenElement;

    if (fullscreenElement === this.getContainer() && !this._isFullscreen) {
      this._setFullscreen(true);
      this.fire('fullscreenchange');
    } else if (fullscreenElement !== this.getContainer() && this._isFullscreen) {
      this._setFullscreen(false);
      this.fire('fullscreenchange');
    }
  }
});

L.Map.mergeOptions({
  fullscreenControl: false
});

L.Map.addInitHook(function () {
  if (this.options.fullscreenControl) {
    this.fullscreenControl = new L.Control.Fullscreen(this.options.fullscreenControl);
    this.addControl(this.fullscreenControl);
  }

  var fullscreenchange;

  if ('onfullscreenchange' in document) {
    fullscreenchange = 'fullscreenchange';
  } else if ('onmozfullscreenchange' in document) {
    fullscreenchange = 'mozfullscreenchange';
  } else if ('onwebkitfullscreenchange' in document) {
    fullscreenchange = 'webkitfullscreenchange';
  } else if ('onmsfullscreenchange' in document) {
    fullscreenchange = 'MSFullscreenChange';
  }

  if (fullscreenchange) {
    var onFullscreenChange = L.bind(this._onFullscreenChange, this);

    this.whenReady(function () {
      L.DomEvent.on(document, fullscreenchange, onFullscreenChange);
    });

    this.on('unload', function () {
      L.DomEvent.off(document, fullscreenchange, onFullscreenChange);
    });
  }
});

L.control.fullscreen = function (options) {
  return new L.Control.Fullscreen(options);
};

/*
 * Leaflet Heatmap Overlay
 *
 * Copyright (c) 2014, Patrick Wied (http://www.patrick-wied.at)
 * Dual-licensed under the MIT (http://www.opensource.org/licenses/mit-license.php)
 * and the Beerware (http://en.wikipedia.org/wiki/Beerware) license.
 */

L.HeatmapOverlay = L.Layer.extend({
  initialize: function (config) {
    this.cfg = config;
    this._el = L.DomUtil.create('div', 'leaflet-zoom-hide');
    this._data = [];
    this._max = 1;
    this._min = 0;
    this.cfg.container = this._el;
  },

  onAdd: function (map) {
    var size = map.getSize();

    this._map = map;

    this._width = size.x;
    this._height = size.y;

    this._el.style.width = size.x + 'px';
    this._el.style.height = size.y + 'px';
    this._el.style.position = 'absolute';
    this._reset();
    var pane = map.getPanes().overlayPane;
    pane.insertBefore(this._el, pane.firstChild);

    if (!this._heatmap) {
      this._heatmap = h337.create(this.cfg);
    }
    this._draw();
  },
  addTo: function (map) {
    map.addLayer(this);
    return this;
  },
  onRemove: function (map) {
    // remove layer's DOM elements and listeners
    map.getPanes().overlayPane.removeChild(this._el);
  },
  getEvents: function () {
    return {
      resize: this._resize,
      moveend: this._draw,
      viewreset: this._reset
    };
  },
  _draw: function () {
    if (!this._map) {
      return;
    }
    var point = this._map.getPanes().mapPane._leaflet_pos;
    //var point = this._map.latLngToContainerPoint(this._origin);

    // reposition the layer
    L.DomUtil.setPosition(this._el, new L.Point(-Math.round(point.x), -Math.round(point.y)));
    //this._el.style.position = 'absolute';

    this._update();
  },
  _update: function () {
    var bounds, zoom, scale;
    var generatedData = {max: this._max, min: this._min, data: []};

    bounds = this._map.getBounds();
    zoom = this._map.getZoom();
    scale = Math.pow(2, zoom);

    if (this._data.length == 0) {
      return;
    }

    var latLngPoints = [];
    var radiusMultiplier = this.cfg.scaleRadius ? scale : 1;
    var localMax = 0;
    var localMin = 0;
    var valueField = this.cfg.valueField;
    var len = this._data.length;

    while (len--) {
      var entry = this._data[len];
      var value = entry[valueField];
      var latlng = entry.latlng;

      // we don't wanna render points that are not even on the map ;-)
      if (!bounds.contains(latlng)) {
        continue;
      }
      // local max is the maximum within current bounds
      localMax = Math.max(value, localMax);
      localMin = Math.min(value, localMin);

      var point = this._map.latLngToContainerPoint(latlng);
      var latlngPoint = {x: Math.round(point.x), y: Math.round(point.y)};
      latlngPoint[valueField] = value;

      var radius;

      if (entry.radius) {
        radius = entry.radius * radiusMultiplier;
      } else {
        radius = (this.cfg.radius || 2) * radiusMultiplier;
      }
      latlngPoint.radius = radius;
      latLngPoints.push(latlngPoint);
    }
    if (this.cfg.useLocalExtrema) {
      generatedData.max = localMax;
      generatedData.min = localMin;
    }

    generatedData.data = latLngPoints;
    this._heatmap.setData(generatedData);
  },
  setData: function (data) {
    this._max = data.max || this._max;
    this._min = data.min || this._min;
    var latField = this.cfg.latField || 'lat';
    var lngField = this.cfg.lngField || 'lng';
    var valueField = this.cfg.valueField || 'value';

    // transform data to latlngs
    var data = data.data;
    var len = data.length;
    var d = [];

    while (len--) {
      var entry = data[len];
      var latlng = new L.LatLng(entry[latField], entry[lngField]);
      var dataObj = {latlng: latlng};
      dataObj[valueField] = entry[valueField];
      if (entry.radius) {
        dataObj.radius = entry.radius;
      }
      d.push(dataObj);
    }
    this._data = d;
    if (this._data.length == 0) {
      this._heatmap._renderer._clear();
    }
    this._draw();
  },
  // experimential... not ready.
  addData: function (pointOrArray) {
    if (pointOrArray.length > 0) {
      var len = pointOrArray.length;
      while (len--) {
        this.addData(pointOrArray[len]);
      }
    } else {
      var latField = this.cfg.latField || 'lat';
      var lngField = this.cfg.lngField || 'lng';
      var valueField = this.cfg.valueField || 'value';
      var entry = pointOrArray;
      var latlng = new L.LatLng(entry[latField], entry[lngField]);
      var dataObj = {latlng: latlng};

      dataObj[valueField] = entry[valueField];
      this._max = Math.max(this._max, dataObj[valueField]);
      this._min = Math.min(this._min, dataObj[valueField]);

      if (entry.radius) {
        dataObj.radius = entry.radius;
      }
      this._data.push(dataObj);
      this._draw();
    }
  },
  _reset: function () {
    this._origin = this._map.layerPointToLatLng(new L.Point(0, 0));
    var size = this._map.getSize();
    if (this._width !== size.x || this._height !== size.y) {
      this._width = size.x;
      this._height = size.y;

      this._el.style.width = this._width + 'px';
      this._el.style.height = this._height + 'px';

      this._heatmap._renderer.setDimensions(this._width, this._height);
    }
    this._draw();
  },
  _resize: function (e) {
    if (this._width !== e.newSize.x || this._height !== e.newSize.y) {
      this._width = e.newSize.x;
      this._height = e.newSize.y;
      this._el.style.width = e.newSize.x + 'px';
      this._el.style.height = e.newSize.y + 'px';
      this._heatmap._renderer.setDimensions(e.newSize.x, e.newSize.y);
    }
    this._draw();
  }
});
L.heatmapOverlay = function (opts) {
  return new L.HeatmapOverlay(opts);
};

/**L.Control.Home is a control to allow users show map i**/
L.Control.Home = L.Control.extend({
  options: {
    position: 'topleft',
    homeTitle: "Go to home map view"
  },
  onAdd: function (map) {
    if (!this.options.center) {
      this.options.center = map.getCenter();
    }
    if (!this.options.zoom) {
      this.options.zoom = map.getZoom();
    }
    options = this.options;
    // Create toolbar
    var controlName = 'leaflet-control-navbar', container;
    if (map.zoomControl) {
      container = map.zoomControl._container;
    } else {
      container = L.DomUtil.create('div', controlName + ' leaflet-bar');
    }
    // Add toolbar buttons
    this._homeButton = this._createButton(options.homeTitle, controlName + '-home', container, this._goHome);
    // Set intial view to home
    map.setView(options.center, options.zoom);
    return container;
  },
  _goHome: function () {
    this._map.setView(this.options.center, this.options.zoom);
  },
  _createButton: function (title, className, container, fn) {
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
});
L.Map.addInitHook(function () {
  if (this.options.homeControl) {
    this.addControl(new L.Control.Home());
  }
});
L.control.Home = function (options) {
  return new L.Control.Home(options);
}
/**L.Control.Info is a control to allow users show Infos**/
L.Control.InfoExt = L.Control.extend({
  options: {
    position: 'topright'
  },
  initialize: function (options) {
    L.setOptions(this, options);
    this._info = {};
  },
  onAdd: function (map) {
    this._container = L.DomUtil.create('div', 'control-info');
    this._content = L.DomUtil.create('div', 'info-container', this._container);

    var link = L.DomUtil.create('a', 'info-toggle info-icon info-icon-pos', this._container);
    link.href = '#';

    L.DomEvent.addListener(link, 'click', this._showInfo, this);
    L.DomEvent.disableClickPropagation(this._container);
    this._update();
    return this._container;
  },
  update: function (text) {
    this._info = {};
    this._info[text] = true;
    return this._update();
  },
  addInfo: function (text) {
    if (!text) return this;
    if (!this._info[text]) this._info[text] = 0;
    this._info[text] = true;
    return this._update();
  },
  removeInfo: function (text) {
    if (!text) {
      this._info = {};
      return this;
    }
    if (this._info[text]) this._info[text] = false;
    return this._update();
  },
  showInfo: function () {
    L.DomUtil.addClass(this._container, 'active');
    this._active = true;
    this._update();
  },
  _showInfo: function (e) {
    L.DomEvent.preventDefault(e);
    if (this._active === true) return this._hidecontent();

    L.DomUtil.addClass(this._container, 'active');
    this._active = true;
    this._update();
  },
  _hidecontent: function () {
    this._content.innerHTML = '';
    this._active = false;
    L.DomUtil.removeClass(this._container, 'active');
    return;
  },

  _update: function () {
    if (!this._map) {
      return this;
    }
    this._content.innerHTML = '';
    var hide = 'none';
    var info = [];

    for (var i in this._info) {
      if (this._info.hasOwnProperty(i) && this._info[i]) {
        info.push(i);
        hide = 'block';
      }
    }
    this._content.innerHTML += info.join('');
    // If there are no results in _info then hide this.
    this._container.style.display = hide;
    return this;
  }
});
L.control.infoExt = function (options) {
  return new L.Control.InfoExt(options);
};

L.Control.Info = L.Control.extend({
  options: {
    position: 'topright'
  },
  initialize: function (options) {
    L.setOptions(this, options);
  },
  onAdd: function (map) {
    this._div = L.DomUtil.create('div', 'info');
    this.update('');
    return this._div;
  },
  update: function (infos) {
    if (L.Util.isArray(infos)) {
      this._div.innerHTML = infos.join('<br>');
    } else {
      this._div.innerHTML = infos;
    }
    return this;
  }
});
L.control.info = function (options) {
  return new L.Control.Info(options);
};
/*
 * L.CircleMarker is a circle overlay with a permanent pixel radius.
 */
L.CircleMarkerLabel = L.CircleMarker.extend({
  options: {
    radius: 10,
    weight: 2,
    text: '',
    fontSize: 15,
    textColor: 'white'
  },

  initialize: function (latlng, options) {
    L.setOptions(this, options);
    this._radius = this.options.radius;
    L.CircleMarker.prototype.initialize.call(this, latlng, options);
  },
  onRemove: function () {
    if (this._text) {
      this._renderer._rootGroup.removeChild(this._text);
      delete this._text;
    }
    L.CircleMarker.prototype.onRemove.call(this);
  },
  onAdd: function (map) {
    L.CircleMarker.prototype.onAdd.call(this, map);
    if (this._text) {
      this._renderer._rootGroup.appendChild(this._text);
    }
    return this;
  },
  _project: function () {
    this._point = this._map.latLngToLayerPoint(this._latlng);
    if (this.options.text) {
      if (!this._text) {
        this._text = L.SVG.create('text');
        this._text.setAttribute('text-anchor', 'middle');
        this._text.setAttribute('font-size', this.options.fontSize);
        this._text.setAttribute('fill', this.options.textColor);
        this._text.appendChild(document.createTextNode(this.options.text));
        L.DomUtil.addClass(this._text, 'leaflet-clickable');
      }
      this._text.setAttribute("x", this._point.x); // Position the text
      this._text.setAttribute("y", this._point.y + 5);
    }
    this._updateBounds();
  }
});

L.circleMarkerLabel = function (latlng, options) {
  return new L.CircleMarkerLabel(latlng, options);
};

/**L.Control.Legend is a control to allow users show legends**/
L.Control.Legend = L.Control.extend({
  options: {
    position: 'bottomright',
    autoZIndex: true
  },
  initialize: function (options) {
    L.setOptions(this, options);
    this._legends = {};
  },
  onAdd: function (map) {
    this._container = L.DomUtil.create('div', 'control-legend legend');
    this._content = L.DomUtil.create('div', 'legend-container', this._container);

    var link = L.DomUtil.create('a', 'legend-toggle legend-icon', this._container);
    link.href = '#';

    L.DomEvent.addListener(link, 'click', this._showInfo, this);
    L.DomEvent.disableClickPropagation(this._container);
    this._update();
    return this._container;
  },
  update: function (text) {
    this.removeLegend();
    this.addLegend(text);
  },
  addLegend: function (text) {
    if (!text) {
      return this;
    }
    if (!this._legends[text]) {
      this._legends[text] = 0;
    }
    this._legends[text]++;
    return this._update();
  },
  removeLegend: function (text) {
    if (!text) {
      this._legends = {};
      return this;
    }
    if (this._legends[text]) this._legends[text]--;
    return this._update();
  },
  _showInfo: function (e) {
    L.DomEvent.preventDefault(e);
    if (this._active === true) return this._hidecontent();

    L.DomUtil.addClass(this._container, 'active');
    this._active = true;
    this._update();
  },

  _hidecontent: function () {
    this._content.innerHTML = '';
    this._active = false;
    L.DomUtil.removeClass(this._container, 'active');
    return;
  },
  _update: function () {
    if (!this._map) {
      return this;
    }
    this._content.innerHTML = '';
    var hide = 'none';
    for (var i in this._legends) {
      if (this._legends.hasOwnProperty(i) && this._legends[i]) {
        var div = L.DomUtil.create('div', 'map-legend', this._content);
        div.innerHTML = i;
        hide = 'block';
      }
    }
    // hide the control entirely unless there is at least one legend;
    // otherwise there will be a small grey blemish on the map.
    this._container.style.display = hide;
    return this;
  },
  hide: function () {
    this._container.style.display = 'none';
  },
  show: function () {
    this._container.style.display = 'block';
  },
  isShow:function () {
    var flag = false;
    if(this._container.style.display == 'block'){
      flag = true;
    }
    return flag;
  }
});

L.control.legend = function (options) {
  return new L.Control.Legend(options);
}

L.Control.Loading = L.Control.extend({
  options: {
    position: 'topleft',
    separate: false,
    zoomControl: null
  },

  initialize: function (options) {
    L.setOptions(this, options);
    this._dataLoaders = {};

    // Try to set the zoom control this control is attached to from the
    // options
    if (this.options.zoomControl !== null) {
      this.zoomControl = this.options.zoomControl;
    }
  },

  onAdd: function (map) {
    // Try to set the zoom control this control is attached to from the map
    // the control is being added to
    if (!this.options.separate && !this.zoomControl) {
      if (map.zoomControl) {
        this.zoomControl = map.zoomControl;
      } else if (map.zoomsliderControl) {
        this.zoomControl = map.zoomsliderControl;
      }
    }

    // Create the loading indicator
    var classes = 'leaflet-control-loading';
    var container;
    if (this.zoomControl && !this.options.separate) {
      // If there is a zoom control, hook into the bottom of it
      container = this.zoomControl._container;
      // These classes are no longer used as of Leaflet 0.6
      classes += ' leaflet-bar-part-bottom leaflet-bar-part last';

      // Loading control will be added to the zoom control. So the visible last element is not the
      // last dom element anymore. So add the part-bottom class.
      L.DomUtil.addClass(this._getLastControlButton(), 'leaflet-bar-part-bottom');
    } else {
      // Otherwise, create a container for the indicator
      container = L.DomUtil.create('div', 'leaflet-control-zoom leaflet-bar');
    }
    this._indicator = L.DomUtil.create('a', classes, container);
    return container;
  },

  showIndicator: function () {
    // Show loading indicator
    L.DomUtil.addClass(this._indicator, 'is-loading');

    // If zoomControl exists, make the zoom-out button not last
    if (!this.options.separate) {
      if (this.zoomControl instanceof L.Control.Zoom) {
        L.DomUtil.removeClass(this._getLastControlButton(), 'leaflet-bar-part-bottom');
      } else if (typeof L.Control.Zoomslider === 'function' && this.zoomControl instanceof L.Control.Zoomslider) {
        L.DomUtil.removeClass(this.zoomControl._ui.zoomOut, 'leaflet-bar-part-bottom');
      }
    }
  },

  hideIndicator: function () {
    // Hide loading indicator
    L.DomUtil.removeClass(this._indicator, 'is-loading');

    // If zoomControl exists, make the zoom-out button last
    if (!this.options.separate) {
      if (this.zoomControl instanceof L.Control.Zoom) {
        L.DomUtil.addClass(this._getLastControlButton(), 'leaflet-bar-part-bottom');
      } else if (typeof L.Control.Zoomslider === 'function' && this.zoomControl instanceof L.Control.Zoomslider) {
        L.DomUtil.addClass(this.zoomControl._ui.zoomOut, 'leaflet-bar-part-bottom');
      }
    }
  },

  _getLastControlButton: function () {
    var container = this.zoomControl._container,
      index = container.children.length - 1;

    // Find the last visible control button that is not our loading
    // indicator
    while (index > 0) {
      var button = container.children[index];
      if (!(this._indicator === button || button.offsetWidth === 0 || button.offsetHeight === 0)) {
        break;
      }
      index--;
    }

    return container.children[index];
  }
});

L.Map.addInitHook(function () {
  if (this.options.loadingControl) {
    this.loadingControl = (new L.Control.Loading()).addTo(this);
  }
});

L.Control.loading = function (options) {
  return new L.Control.Loading(options);
};
/*
 * L.MarkerClusterGroup extends L.FeatureGroup by clustering the markers contained within
 */
L.MarkerClusterGroup = L.MarkerClusterGroup = L.FeatureGroup.extend({
  options: {
    maxClusterRadius: 80, //A cluster will cover at most this many pixels from its center
    iconCreateFunction: null,
    clusterPane: L.Marker.prototype.options.pane,

    spiderfyOnMaxZoom: true,
    showCoverageOnHover: true,
    zoomToBoundsOnClick: true,
    singleMarkerMode: false,

    disableClusteringAtZoom: null,

    // Setting this to false prevents the removal of any clusters outside of the viewpoint, which
    // is the default behaviour for performance reasons.
    removeOutsideVisibleBounds: true,

    // Set to false to disable all animations (zoom and spiderfy).
    // If false, option animateAddingMarkers below has no effect.
    // If L.DomUtil.TRANSITION is falsy, this option has no effect.
    animate: true,

    //Whether to animate adding markers after adding the MarkerClusterGroup to the map
    // If you are adding individual markers set to true, if adding bulk markers leave false for massive performance gains.
    animateAddingMarkers: false,

    //Increase to increase the distance away that spiderfied markers appear from the center
    spiderfyDistanceMultiplier: 1,

    // Make it possible to specify a polyline options on a spider leg
    spiderLegPolylineOptions: {weight: 1.5, color: '#222', opacity: 0.5},

    // When bulk adding layers, adds markers in chunks. Means addLayers may not add all the layers in the call, others will be loaded during setTimeouts
    chunkedLoading: false,
    chunkInterval: 200, // process markers for a maximum of ~ n milliseconds (then trigger the chunkProgress callback)
    chunkDelay: 50, // at the end of each interval, give n milliseconds back to system/browser
    chunkProgress: null, // progress callback: function(processed, total, elapsed) (e.g. for a progress indicator)

    //Options to pass to the L.Polygon constructor
    polygonOptions: {}
  },

  initialize: function (options) {
    L.Util.setOptions(this, options);
    if (!this.options.iconCreateFunction) {
      this.options.iconCreateFunction = this._defaultIconCreateFunction;
    }

    this._featureGroup = L.featureGroup();
    this._featureGroup.addEventParent(this);

    this._nonPointGroup = L.featureGroup();
    this._nonPointGroup.addEventParent(this);

    this._inZoomAnimation = 0;
    this._needsClustering = [];
    this._needsRemoving = []; //Markers removed while we aren't on the map need to be kept track of
    //The bounds of the currently shown area (from _getExpandedVisibleBounds) Updated on zoom/move
    this._currentShownBounds = null;

    this._queue = [];

    this._childMarkerEventHandlers = {
      'dragstart': this._childMarkerDragStart,
      'move': this._childMarkerMoved,
      'dragend': this._childMarkerDragEnd,
    };

    // Hook the appropriate animation methods.
    var animate = L.DomUtil.TRANSITION && this.options.animate;
    L.extend(this, animate ? this._withAnimation : this._noAnimation);
    // Remember which MarkerCluster class to instantiate (animated or not).
    this._markerCluster = animate ? L.MarkerCluster : L.MarkerClusterNonAnimated;
  },

  addLayer: function (layer) {

    if (layer instanceof L.LayerGroup) {
      return this.addLayers([layer]);
    }

    //Don't cluster non point data
    if (!layer.getLatLng) {
      this._nonPointGroup.addLayer(layer);
      this.fire('layeradd', {layer: layer});
      return this;
    }

    if (!this._map) {
      this._needsClustering.push(layer);
      this.fire('layeradd', {layer: layer});
      return this;
    }

    if (this.hasLayer(layer)) {
      return this;
    }


    //If we have already clustered we'll need to add this one to a cluster

    if (this._unspiderfy) {
      this._unspiderfy();
    }

    this._addLayer(layer, this._maxZoom);
    this.fire('layeradd', {layer: layer});

    // Refresh bounds and weighted positions.
    this._topClusterLevel._recalculateBounds();

    this._refreshClustersIcons();

    //Work out what is visible
    var visibleLayer = layer,
      currentZoom = this._zoom;
    if (layer.__parent) {
      while (visibleLayer.__parent._zoom >= currentZoom) {
        visibleLayer = visibleLayer.__parent;
      }
    }

    if (this._currentShownBounds.contains(visibleLayer.getLatLng())) {
      if (this.options.animateAddingMarkers) {
        this._animationAddLayer(layer, visibleLayer);
      } else {
        this._animationAddLayerNonAnimated(layer, visibleLayer);
      }
    }
    return this;
  },

  removeLayer: function (layer) {

    if (layer instanceof L.LayerGroup) {
      return this.removeLayers([layer]);
    }

    //Non point layers
    if (!layer.getLatLng) {
      this._nonPointGroup.removeLayer(layer);
      this.fire('layerremove', {layer: layer});
      return this;
    }

    if (!this._map) {
      if (!this._arraySplice(this._needsClustering, layer) && this.hasLayer(layer)) {
        this._needsRemoving.push({layer: layer, latlng: layer._latlng});
      }
      this.fire('layerremove', {layer: layer});
      return this;
    }

    if (!layer.__parent) {
      return this;
    }

    if (this._unspiderfy) {
      this._unspiderfy();
      this._unspiderfyLayer(layer);
    }

    //Remove the marker from clusters
    this._removeLayer(layer, true);
    this.fire('layerremove', {layer: layer});

    // Refresh bounds and weighted positions.
    this._topClusterLevel._recalculateBounds();

    this._refreshClustersIcons();

    layer.off(this._childMarkerEventHandlers, this);

    if (this._featureGroup.hasLayer(layer)) {
      this._featureGroup.removeLayer(layer);
      if (layer.clusterShow) {
        layer.clusterShow();
      }
    }

    return this;
  },

  //Takes an array of markers and adds them in bulk
  addLayers: function (layersArray, skipLayerAddEvent) {
    if (!L.Util.isArray(layersArray)) {
      return this.addLayer(layersArray);
    }

    var fg = this._featureGroup,
      npg = this._nonPointGroup,
      chunked = this.options.chunkedLoading,
      chunkInterval = this.options.chunkInterval,
      chunkProgress = this.options.chunkProgress,
      l = layersArray.length,
      offset = 0,
      originalArray = true,
      m;

    if (this._map) {
      var started = (new Date()).getTime();
      var process = L.bind(function () {
        var start = (new Date()).getTime();
        for (; offset < l; offset++) {
          if (chunked && offset % 200 === 0) {
            // every couple hundred markers, instrument the time elapsed since processing started:
            var elapsed = (new Date()).getTime() - start;
            if (elapsed > chunkInterval) {
              break; // been working too hard, time to take a break :-)
            }
          }

          m = layersArray[offset];

          // Group of layers, append children to layersArray and skip.
          // Side effects:
          // - Total increases, so chunkProgress ratio jumps backward.
          // - Groups are not included in this group, only their non-group child layers (hasLayer).
          // Changing array length while looping does not affect performance in current browsers:
          // http://jsperf.com/for-loop-changing-length/6
          if (m instanceof L.LayerGroup) {
            if (originalArray) {
              layersArray = layersArray.slice();
              originalArray = false;
            }
            this._extractNonGroupLayers(m, layersArray);
            l = layersArray.length;
            continue;
          }

          //Not point data, can't be clustered
          if (!m.getLatLng) {
            npg.addLayer(m);
            if (!skipLayerAddEvent) {
              this.fire('layeradd', {layer: m});
            }
            continue;
          }

          if (this.hasLayer(m)) {
            continue;
          }

          this._addLayer(m, this._maxZoom);
          if (!skipLayerAddEvent) {
            this.fire('layeradd', {layer: m});
          }

          //If we just made a cluster of size 2 then we need to remove the other marker from the map (if it is) or we never will
          if (m.__parent) {
            if (m.__parent.getChildCount() === 2) {
              var markers = m.__parent.getAllChildMarkers(),
                otherMarker = markers[0] === m ? markers[1] : markers[0];
              fg.removeLayer(otherMarker);
            }
          }
        }

        if (chunkProgress) {
          // report progress and time elapsed:
          chunkProgress(offset, l, (new Date()).getTime() - started);
        }

        // Completed processing all markers.
        if (offset === l) {

          // Refresh bounds and weighted positions.
          this._topClusterLevel._recalculateBounds();

          this._refreshClustersIcons();

          this._topClusterLevel._recursivelyAddChildrenToMap(null, this._zoom, this._currentShownBounds);
        } else {
          setTimeout(process, this.options.chunkDelay);
        }
      }, this);

      process();
    } else {
      var needsClustering = this._needsClustering;

      for (; offset < l; offset++) {
        m = layersArray[offset];

        // Group of layers, append children to layersArray and skip.
        if (m instanceof L.LayerGroup) {
          if (originalArray) {
            layersArray = layersArray.slice();
            originalArray = false;
          }
          this._extractNonGroupLayers(m, layersArray);
          l = layersArray.length;
          continue;
        }

        //Not point data, can't be clustered
        if (!m.getLatLng) {
          npg.addLayer(m);
          continue;
        }

        if (this.hasLayer(m)) {
          continue;
        }

        needsClustering.push(m);
      }
    }
    return this;
  },

  //Takes an array of markers and removes them in bulk
  removeLayers: function (layersArray) {
    var i, m,
      l = layersArray.length,
      fg = this._featureGroup,
      npg = this._nonPointGroup,
      originalArray = true;

    if (!this._map) {
      for (i = 0; i < l; i++) {
        m = layersArray[i];

        // Group of layers, append children to layersArray and skip.
        if (m instanceof L.LayerGroup) {
          if (originalArray) {
            layersArray = layersArray.slice();
            originalArray = false;
          }
          this._extractNonGroupLayers(m, layersArray);
          l = layersArray.length;
          continue;
        }

        this._arraySplice(this._needsClustering, m);
        npg.removeLayer(m);
        if (this.hasLayer(m)) {
          this._needsRemoving.push({layer: m, latlng: m._latlng});
        }
        this.fire('layerremove', {layer: m});
      }
      return this;
    }

    if (this._unspiderfy) {
      this._unspiderfy();

      // Work on a copy of the array, so that next loop is not affected.
      var layersArray2 = layersArray.slice(),
        l2 = l;
      for (i = 0; i < l2; i++) {
        m = layersArray2[i];

        // Group of layers, append children to layersArray and skip.
        if (m instanceof L.LayerGroup) {
          this._extractNonGroupLayers(m, layersArray2);
          l2 = layersArray2.length;
          continue;
        }

        this._unspiderfyLayer(m);
      }
    }

    for (i = 0; i < l; i++) {
      m = layersArray[i];

      // Group of layers, append children to layersArray and skip.
      if (m instanceof L.LayerGroup) {
        if (originalArray) {
          layersArray = layersArray.slice();
          originalArray = false;
        }
        this._extractNonGroupLayers(m, layersArray);
        l = layersArray.length;
        continue;
      }

      if (!m.__parent) {
        npg.removeLayer(m);
        this.fire('layerremove', {layer: m});
        continue;
      }

      this._removeLayer(m, true, true);
      this.fire('layerremove', {layer: m});

      if (fg.hasLayer(m)) {
        fg.removeLayer(m);
        if (m.clusterShow) {
          m.clusterShow();
        }
      }
    }

    // Refresh bounds and weighted positions.
    this._topClusterLevel._recalculateBounds();

    this._refreshClustersIcons();

    //Fix up the clusters and markers on the map
    this._topClusterLevel._recursivelyAddChildrenToMap(null, this._zoom, this._currentShownBounds);

    return this;
  },

  //Removes all layers from the MarkerClusterGroup
  clearLayers: function () {
    //Need our own special implementation as the LayerGroup one doesn't work for us

    //If we aren't on the map (yet), blow away the markers we know of
    if (!this._map) {
      this._needsClustering = [];
      delete this._gridClusters;
      delete this._gridUnclustered;
    }

    if (this._noanimationUnspiderfy) {
      this._noanimationUnspiderfy();
    }

    //Remove all the visible layers
    this._featureGroup.clearLayers();
    this._nonPointGroup.clearLayers();

    this.eachLayer(function (marker) {
      marker.off(this._childMarkerEventHandlers, this);
      delete marker.__parent;
    }, this);

    if (this._map) {
      //Reset _topClusterLevel and the DistanceGrids
      this._generateInitialClusters();
    }

    return this;
  },

  //Override FeatureGroup.getBounds as it doesn't work
  getBounds: function () {
    var bounds = new L.LatLngBounds();

    if (this._topClusterLevel) {
      bounds.extend(this._topClusterLevel._bounds);
    }

    for (var i = this._needsClustering.length - 1; i >= 0; i--) {
      bounds.extend(this._needsClustering[i].getLatLng());
    }

    bounds.extend(this._nonPointGroup.getBounds());

    return bounds;
  },

  //Overrides LayerGroup.eachLayer
  eachLayer: function (method, context) {
    var markers = this._needsClustering.slice(),
      needsRemoving = this._needsRemoving,
      thisNeedsRemoving, i, j;

    if (this._topClusterLevel) {
      this._topClusterLevel.getAllChildMarkers(markers);
    }

    for (i = markers.length - 1; i >= 0; i--) {
      thisNeedsRemoving = true;

      for (j = needsRemoving.length - 1; j >= 0; j--) {
        if (needsRemoving[j].layer === markers[i]) {
          thisNeedsRemoving = false;
          break;
        }
      }

      if (thisNeedsRemoving) {
        method.call(context, markers[i]);
      }
    }

    this._nonPointGroup.eachLayer(method, context);
  },

  //Overrides LayerGroup.getLayers
  getLayers: function () {
    var layers = [];
    this.eachLayer(function (l) {
      layers.push(l);
    });
    return layers;
  },

  //Overrides LayerGroup.getLayer, WARNING: Really bad performance
  getLayer: function (id) {
    var result = null;

    id = parseInt(id, 10);

    this.eachLayer(function (l) {
      if (L.stamp(l) === id) {
        result = l;
      }
    });

    return result;
  },

  //Returns true if the given layer is in this MarkerClusterGroup
  hasLayer: function (layer) {
    if (!layer) {
      return false;
    }

    var i, anArray = this._needsClustering;

    for (i = anArray.length - 1; i >= 0; i--) {
      if (anArray[i] === layer) {
        return true;
      }
    }

    anArray = this._needsRemoving;
    for (i = anArray.length - 1; i >= 0; i--) {
      if (anArray[i].layer === layer) {
        return false;
      }
    }

    return !!(layer.__parent && layer.__parent._group === this) || this._nonPointGroup.hasLayer(layer);
  },

  //Zoom down to show the given layer (spiderfying if necessary) then calls the callback
  zoomToShowLayer: function (layer, callback) {

    if (typeof callback !== 'function') {
      callback = function () {
      };
    }

    var showMarker = function () {
      if ((layer._icon || layer.__parent._icon) && !this._inZoomAnimation) {
        this._map.off('moveend', showMarker, this);
        this.off('animationend', showMarker, this);

        if (layer._icon) {
          callback();
        } else if (layer.__parent._icon) {
          this.once('spiderfied', callback, this);
          layer.__parent.spiderfy();
        }
      }
    };

    if (layer._icon && this._map.getBounds().contains(layer.getLatLng())) {
      //Layer is visible ond on screen, immediate return
      callback();
    } else if (layer.__parent._zoom < Math.round(this._map._zoom)) {
      //Layer should be visible at this zoom level. It must not be on screen so just pan over to it
      this._map.on('moveend', showMarker, this);
      this._map.panTo(layer.getLatLng());
    } else {
      this._map.on('moveend', showMarker, this);
      this.on('animationend', showMarker, this);
      layer.__parent.zoomToBounds();
    }
  },

  //Overrides FeatureGroup.onAdd
  onAdd: function (map) {
    this._map = map;
    var i, l, layer;

    if (!isFinite(this._map.getMaxZoom())) {
      throw "Map has no maxZoom specified";
    }

    this._featureGroup.addTo(map);
    this._nonPointGroup.addTo(map);

    if (!this._gridClusters) {
      this._generateInitialClusters();
    }

    this._maxLat = map.options.crs.projection.MAX_LATITUDE;

    //Restore all the positions as they are in the MCG before removing them
    for (i = 0, l = this._needsRemoving.length; i < l; i++) {
      layer = this._needsRemoving[i];
      layer.newlatlng = layer.layer._latlng;
      layer.layer._latlng = layer.latlng;
    }
    //Remove them, then restore their new positions
    for (i = 0, l = this._needsRemoving.length; i < l; i++) {
      layer = this._needsRemoving[i];
      this._removeLayer(layer.layer, true);
      layer.layer._latlng = layer.newlatlng;
    }
    this._needsRemoving = [];

    //Remember the current zoom level and bounds
    this._zoom = Math.round(this._map._zoom);
    this._currentShownBounds = this._getExpandedVisibleBounds();

    this._map.on('zoomend', this._zoomEnd, this);
    this._map.on('moveend', this._moveEnd, this);

    if (this._spiderfierOnAdd) { //TODO FIXME: Not sure how to have spiderfier add something on here nicely
      this._spiderfierOnAdd();
    }

    this._bindEvents();

    //Actually add our markers to the map:
    l = this._needsClustering;
    this._needsClustering = [];
    this.addLayers(l, true);
  },

  //Overrides FeatureGroup.onRemove
  onRemove: function (map) {
    map.off('zoomend', this._zoomEnd, this);
    map.off('moveend', this._moveEnd, this);

    this._unbindEvents();

    //In case we are in a cluster animation
    this._map._mapPane.className = this._map._mapPane.className.replace(' leaflet-cluster-anim', '');

    if (this._spiderfierOnRemove) { //TODO FIXME: Not sure how to have spiderfier add something on here nicely
      this._spiderfierOnRemove();
    }

    delete this._maxLat;

    //Clean up all the layers we added to the map
    this._hideCoverage();
    this._featureGroup.remove();
    this._nonPointGroup.remove();

    this._featureGroup.clearLayers();

    this._map = null;
  },

  getVisibleParent: function (marker) {
    var vMarker = marker;
    while (vMarker && !vMarker._icon) {
      vMarker = vMarker.__parent;
    }
    return vMarker || null;
  },

  //Remove the given object from the given array
  _arraySplice: function (anArray, obj) {
    for (var i = anArray.length - 1; i >= 0; i--) {
      if (anArray[i] === obj) {
        anArray.splice(i, 1);
        return true;
      }
    }
  },

  /**
   * Removes a marker from all _gridUnclustered zoom levels, starting at the supplied zoom.
   * @param marker to be removed from _gridUnclustered.
   * @param z integer bottom start zoom level (included)
   * @private
   */
  _removeFromGridUnclustered: function (marker, z) {
    var map = this._map,
      gridUnclustered = this._gridUnclustered,
      minZoom = Math.floor(this._map.getMinZoom());

    for (; z >= minZoom; z--) {
      if (!gridUnclustered[z].removeObject(marker, map.project(marker.getLatLng(), z))) {
        break;
      }
    }
  },

  _childMarkerDragStart: function (e) {
    e.target.__dragStart = e.target._latlng;
  },

  _childMarkerMoved: function (e) {
    if (!this._ignoreMove && !e.target.__dragStart) {
      var isPopupOpen = e.target._popup && e.target._popup.isOpen();

      this._moveChild(e.target, e.oldLatLng, e.latlng);

      if (isPopupOpen) {
        e.target.openPopup();
      }
    }
  },

  _moveChild: function (layer, from, to) {
    layer._latlng = from;
    this.removeLayer(layer);

    layer._latlng = to;
    this.addLayer(layer);
  },

  _childMarkerDragEnd: function (e) {
    if (e.target.__dragStart) {
      this._moveChild(e.target, e.target.__dragStart, e.target._latlng);
    }
    delete e.target.__dragStart;
  },


  //Internal function for removing a marker from everything.
  //dontUpdateMap: set to true if you will handle updating the map manually (for bulk functions)
  _removeLayer: function (marker, removeFromDistanceGrid, dontUpdateMap) {
    var gridClusters = this._gridClusters,
      gridUnclustered = this._gridUnclustered,
      fg = this._featureGroup,
      map = this._map,
      minZoom = Math.floor(this._map.getMinZoom());

    //Remove the marker from distance clusters it might be in
    if (removeFromDistanceGrid) {
      this._removeFromGridUnclustered(marker, this._maxZoom);
    }

    //Work our way up the clusters removing them as we go if required
    var cluster = marker.__parent,
      markers = cluster._markers,
      otherMarker;

    //Remove the marker from the immediate parents marker list
    this._arraySplice(markers, marker);

    while (cluster) {
      cluster._childCount--;
      cluster._boundsNeedUpdate = true;

      if (cluster._zoom < minZoom) {
        //Top level, do nothing
        break;
      } else if (removeFromDistanceGrid && cluster._childCount <= 1) { //Cluster no longer required
        //We need to push the other marker up to the parent
        otherMarker = cluster._markers[0] === marker ? cluster._markers[1] : cluster._markers[0];

        //Update distance grid
        gridClusters[cluster._zoom].removeObject(cluster, map.project(cluster._cLatLng, cluster._zoom));
        gridUnclustered[cluster._zoom].addObject(otherMarker, map.project(otherMarker.getLatLng(), cluster._zoom));

        //Move otherMarker up to parent
        this._arraySplice(cluster.__parent._childClusters, cluster);
        cluster.__parent._markers.push(otherMarker);
        otherMarker.__parent = cluster.__parent;

        if (cluster._icon) {
          //Cluster is currently on the map, need to put the marker on the map instead
          fg.removeLayer(cluster);
          if (!dontUpdateMap) {
            fg.addLayer(otherMarker);
          }
        }
      } else {
        cluster._iconNeedsUpdate = true;
      }

      cluster = cluster.__parent;
    }

    delete marker.__parent;
  },

  _isOrIsParent: function (el, oel) {
    while (oel) {
      if (el === oel) {
        return true;
      }
      oel = oel.parentNode;
    }
    return false;
  },

  //Override L.Evented.fire
  fire: function (type, data, propagate) {
    if (data && data.layer instanceof L.MarkerCluster) {
      //Prevent multiple clustermouseover/off events if the icon is made up of stacked divs (Doesn't work in ie <= 8, no relatedTarget)
      if (data.originalEvent && this._isOrIsParent(data.layer._icon, data.originalEvent.relatedTarget)) {
        return;
      }
      type = 'cluster' + type;
    }

    L.FeatureGroup.prototype.fire.call(this, type, data, propagate);
  },

  //Override L.Evented.listens
  listens: function (type, propagate) {
    return L.FeatureGroup.prototype.listens.call(this, type, propagate) || L.FeatureGroup.prototype.listens.call(this, 'cluster' + type, propagate);
  },

  //Default functionality
  _defaultIconCreateFunction: function (cluster) {
    var childCount = cluster.getChildCount();

    var c = ' marker-cluster-';
    if (childCount < 10) {
      c += 'small';
    } else if (childCount < 100) {
      c += 'medium';
    } else {
      c += 'large';
    }

    return new L.DivIcon({
      html: '<div><span>' + childCount + '</span></div>',
      className: 'marker-cluster' + c,
      iconSize: new L.Point(40, 40)
    });
  },

  _bindEvents: function () {
    var map = this._map,
      spiderfyOnMaxZoom = this.options.spiderfyOnMaxZoom,
      showCoverageOnHover = this.options.showCoverageOnHover,
      zoomToBoundsOnClick = this.options.zoomToBoundsOnClick;

    //Zoom on cluster click or spiderfy if we are at the lowest level
    if (spiderfyOnMaxZoom || zoomToBoundsOnClick) {
      this.on('clusterclick', this._zoomOrSpiderfy, this);
    }

    //Show convex hull (boundary) polygon on mouse over
    if (showCoverageOnHover) {
      this.on('clustermouseover', this._showCoverage, this);
      this.on('clustermouseout', this._hideCoverage, this);
      map.on('zoomend', this._hideCoverage, this);
    }
  },

  _zoomOrSpiderfy: function (e) {
    var cluster = e.layer,
      bottomCluster = cluster;

    while (bottomCluster._childClusters.length === 1) {
      bottomCluster = bottomCluster._childClusters[0];
    }

    if (bottomCluster._zoom === this._maxZoom &&
      bottomCluster._childCount === cluster._childCount &&
      this.options.spiderfyOnMaxZoom) {

      // All child markers are contained in a single cluster from this._maxZoom to this cluster.
      cluster.spiderfy();
    } else if (this.options.zoomToBoundsOnClick) {
      cluster.zoomToBounds();
    }

    // Focus the map again for keyboard users.
    if (e.originalEvent && e.originalEvent.keyCode === 13) {
      this._map._container.focus();
    }
  },

  _showCoverage: function (e) {
    var map = this._map;
    if (this._inZoomAnimation) {
      return;
    }
    if (this._shownPolygon) {
      map.removeLayer(this._shownPolygon);
    }
    if (e.layer.getChildCount() > 2 && e.layer !== this._spiderfied) {
      this._shownPolygon = new L.Polygon(e.layer.getConvexHull(), this.options.polygonOptions);
      map.addLayer(this._shownPolygon);
    }
  },

  _hideCoverage: function () {
    if (this._shownPolygon) {
      this._map.removeLayer(this._shownPolygon);
      this._shownPolygon = null;
    }
  },

  _unbindEvents: function () {
    var spiderfyOnMaxZoom = this.options.spiderfyOnMaxZoom,
      showCoverageOnHover = this.options.showCoverageOnHover,
      zoomToBoundsOnClick = this.options.zoomToBoundsOnClick,
      map = this._map;

    if (spiderfyOnMaxZoom || zoomToBoundsOnClick) {
      this.off('clusterclick', this._zoomOrSpiderfy, this);
    }
    if (showCoverageOnHover) {
      this.off('clustermouseover', this._showCoverage, this);
      this.off('clustermouseout', this._hideCoverage, this);
      map.off('zoomend', this._hideCoverage, this);
    }
  },

  _zoomEnd: function () {
    if (!this._map) { //May have been removed from the map by a zoomEnd handler
      return;
    }
    this._mergeSplitClusters();

    this._zoom = Math.round(this._map._zoom);
    this._currentShownBounds = this._getExpandedVisibleBounds();
  },

  _moveEnd: function () {
    if (this._inZoomAnimation) {
      return;
    }

    var newBounds = this._getExpandedVisibleBounds();

    this._topClusterLevel._recursivelyRemoveChildrenFromMap(this._currentShownBounds, Math.floor(this._map.getMinZoom()), this._zoom, newBounds);
    this._topClusterLevel._recursivelyAddChildrenToMap(null, Math.round(this._map._zoom), newBounds);

    this._currentShownBounds = newBounds;
    return;
  },

  _generateInitialClusters: function () {
    var maxZoom = Math.ceil(this._map.getMaxZoom()),
      minZoom = Math.floor(this._map.getMinZoom()),
      radius = this.options.maxClusterRadius,
      radiusFn = radius;

    //If we just set maxClusterRadius to a single number, we need to create
    //a simple function to return that number. Otherwise, we just have to
    //use the function we've passed in.
    if (typeof radius !== "function") {
      radiusFn = function () {
        return radius;
      };
    }

    if (this.options.disableClusteringAtZoom !== null) {
      maxZoom = this.options.disableClusteringAtZoom - 1;
    }
    this._maxZoom = maxZoom;
    this._gridClusters = {};
    this._gridUnclustered = {};

    //Set up DistanceGrids for each zoom
    for (var zoom = maxZoom; zoom >= minZoom; zoom--) {
      this._gridClusters[zoom] = new L.DistanceGrid(radiusFn(zoom));
      this._gridUnclustered[zoom] = new L.DistanceGrid(radiusFn(zoom));
    }

    // Instantiate the appropriate L.MarkerCluster class (animated or not).
    this._topClusterLevel = new this._markerCluster(this, minZoom - 1);
  },

  //Zoom: Zoom to start adding at (Pass this._maxZoom to start at the bottom)
  _addLayer: function (layer, zoom) {
    var gridClusters = this._gridClusters,
      gridUnclustered = this._gridUnclustered,
      minZoom = Math.floor(this._map.getMinZoom()),
      markerPoint, z;

    if (this.options.singleMarkerMode) {
      this._overrideMarkerIcon(layer);
    }

    layer.on(this._childMarkerEventHandlers, this);

    //Find the lowest zoom level to slot this one in
    for (; zoom >= minZoom; zoom--) {
      markerPoint = this._map.project(layer.getLatLng(), zoom); // calculate pixel position

      //Try find a cluster close by
      var closest = gridClusters[zoom].getNearObject(markerPoint);
      if (closest) {
        closest._addChild(layer);
        layer.__parent = closest;
        return;
      }

      //Try find a marker close by to form a new cluster with
      closest = gridUnclustered[zoom].getNearObject(markerPoint);
      if (closest) {
        var parent = closest.__parent;
        if (parent) {
          this._removeLayer(closest, false);
        }

        //Create new cluster with these 2 in it

        var newCluster = new this._markerCluster(this, zoom, closest, layer);
        gridClusters[zoom].addObject(newCluster, this._map.project(newCluster._cLatLng, zoom));
        closest.__parent = newCluster;
        layer.__parent = newCluster;

        //First create any new intermediate parent clusters that don't exist
        var lastParent = newCluster;
        for (z = zoom - 1; z > parent._zoom; z--) {
          lastParent = new this._markerCluster(this, z, lastParent);
          gridClusters[z].addObject(lastParent, this._map.project(closest.getLatLng(), z));
        }
        parent._addChild(lastParent);

        //Remove closest from this zoom level and any above that it is in, replace with newCluster
        this._removeFromGridUnclustered(closest, zoom);

        return;
      }

      //Didn't manage to cluster in at this zoom, record us as a marker here and continue upwards
      gridUnclustered[zoom].addObject(layer, markerPoint);
    }

    //Didn't get in anything, add us to the top
    this._topClusterLevel._addChild(layer);
    layer.__parent = this._topClusterLevel;
    return;
  },

  /**
   * Refreshes the icon of all "dirty" visible clusters.
   * Non-visible "dirty" clusters will be updated when they are added to the map.
   * @private
   */
  _refreshClustersIcons: function () {
    this._featureGroup.eachLayer(function (c) {
      if (c instanceof L.MarkerCluster && c._iconNeedsUpdate) {
        c._updateIcon();
      }
    });
  },

  //Enqueue code to fire after the marker expand/contract has happened
  _enqueue: function (fn) {
    this._queue.push(fn);
    if (!this._queueTimeout) {
      this._queueTimeout = setTimeout(L.bind(this._processQueue, this), 300);
    }
  },
  _processQueue: function () {
    for (var i = 0; i < this._queue.length; i++) {
      this._queue[i].call(this);
    }
    this._queue.length = 0;
    clearTimeout(this._queueTimeout);
    this._queueTimeout = null;
  },

  //Merge and split any existing clusters that are too big or small
  _mergeSplitClusters: function () {
    var mapZoom = Math.round(this._map._zoom);

    //In case we are starting to split before the animation finished
    this._processQueue();

    if (this._zoom < mapZoom && this._currentShownBounds.intersects(this._getExpandedVisibleBounds())) { //Zoom in, split
      this._animationStart();
      //Remove clusters now off screen
      this._topClusterLevel._recursivelyRemoveChildrenFromMap(this._currentShownBounds, Math.floor(this._map.getMinZoom()), this._zoom, this._getExpandedVisibleBounds());

      this._animationZoomIn(this._zoom, mapZoom);

    } else if (this._zoom > mapZoom) { //Zoom out, merge
      this._animationStart();

      this._animationZoomOut(this._zoom, mapZoom);
    } else {
      this._moveEnd();
    }
  },

  //Gets the maps visible bounds expanded in each direction by the size of the screen (so the user cannot see an area we do not cover in one pan)
  _getExpandedVisibleBounds: function () {
    if (!this.options.removeOutsideVisibleBounds) {
      return this._mapBoundsInfinite;
    } else if (L.Browser.mobile) {
      return this._checkBoundsMaxLat(this._map.getBounds());
    }

    return this._checkBoundsMaxLat(this._map.getBounds().pad(1)); // Padding expands the bounds by its own dimensions but scaled with the given factor.
  },

  /**
   * Expands the latitude to Infinity (or -Infinity) if the input bounds reach the map projection maximum defined latitude
   * (in the case of Web/Spherical Mercator, it is 85.0511287798 / see https://en.wikipedia.org/wiki/Web_Mercator#Formulas).
   * Otherwise, the removeOutsideVisibleBounds option will remove markers beyond that limit, whereas the same markers without
   * this option (or outside MCG) will have their position floored (ceiled) by the projection and rendered at that limit,
   * making the user think that MCG "eats" them and never displays them again.
   * @param bounds L.LatLngBounds
   * @returns {L.LatLngBounds}
   * @private
   */
  _checkBoundsMaxLat: function (bounds) {
    var maxLat = this._maxLat;

    if (maxLat !== undefined) {
      if (bounds.getNorth() >= maxLat) {
        bounds._northEast.lat = Infinity;
      }
      if (bounds.getSouth() <= -maxLat) {
        bounds._southWest.lat = -Infinity;
      }
    }

    return bounds;
  },

  //Shared animation code
  _animationAddLayerNonAnimated: function (layer, newCluster) {
    if (newCluster === layer) {
      this._featureGroup.addLayer(layer);
    } else if (newCluster._childCount === 2) {
      newCluster._addToMap();

      var markers = newCluster.getAllChildMarkers();
      this._featureGroup.removeLayer(markers[0]);
      this._featureGroup.removeLayer(markers[1]);
    } else {
      newCluster._updateIcon();
    }
  },

  /**
   * Extracts individual (i.e. non-group) layers from a Layer Group.
   * @param group to extract layers from.
   * @param output {Array} in which to store the extracted layers.
   * @returns {*|Array}
   * @private
   */
  _extractNonGroupLayers: function (group, output) {
    var layers = group.getLayers(),
      i = 0,
      layer;

    output = output || [];

    for (; i < layers.length; i++) {
      layer = layers[i];

      if (layer instanceof L.LayerGroup) {
        this._extractNonGroupLayers(layer, output);
        continue;
      }

      output.push(layer);
    }

    return output;
  },

  /**
   * Implements the singleMarkerMode option.
   * @param layer Marker to re-style using the Clusters iconCreateFunction.
   * @returns {L.Icon} The newly created icon.
   * @private
   */
  _overrideMarkerIcon: function (layer) {
    var icon = layer.options.icon = this.options.iconCreateFunction({
      getChildCount: function () {
        return 1;
      },
      getAllChildMarkers: function () {
        return [layer];
      }
    });

    return icon;
  }
});

// Constant bounds used in case option "removeOutsideVisibleBounds" is set to false.
L.MarkerClusterGroup.include({
  _mapBoundsInfinite: new L.LatLngBounds(new L.LatLng(-Infinity, -Infinity), new L.LatLng(Infinity, Infinity))
});

L.MarkerClusterGroup.include({
  _noAnimation: {
    //Non Animated versions of everything
    _animationStart: function () {
      //Do nothing...
    },
    _animationZoomIn: function (previousZoomLevel, newZoomLevel) {
      this._topClusterLevel._recursivelyRemoveChildrenFromMap(this._currentShownBounds, Math.floor(this._map.getMinZoom()), previousZoomLevel);
      this._topClusterLevel._recursivelyAddChildrenToMap(null, newZoomLevel, this._getExpandedVisibleBounds());

      //We didn't actually animate, but we use this event to mean "clustering animations have finished"
      this.fire('animationend');
    },
    _animationZoomOut: function (previousZoomLevel, newZoomLevel) {
      this._topClusterLevel._recursivelyRemoveChildrenFromMap(this._currentShownBounds, Math.floor(this._map.getMinZoom()), previousZoomLevel);
      this._topClusterLevel._recursivelyAddChildrenToMap(null, newZoomLevel, this._getExpandedVisibleBounds());

      //We didn't actually animate, but we use this event to mean "clustering animations have finished"
      this.fire('animationend');
    },
    _animationAddLayer: function (layer, newCluster) {
      this._animationAddLayerNonAnimated(layer, newCluster);
    }
  },

  _withAnimation: {
    //Animated versions here
    _animationStart: function () {
      this._map._mapPane.className += ' leaflet-cluster-anim';
      this._inZoomAnimation++;
    },

    _animationZoomIn: function (previousZoomLevel, newZoomLevel) {
      var bounds = this._getExpandedVisibleBounds(),
        fg = this._featureGroup,
        minZoom = Math.floor(this._map.getMinZoom()),
        i;

      this._ignoreMove = true;

      //Add all children of current clusters to map and remove those clusters from map
      this._topClusterLevel._recursively(bounds, previousZoomLevel, minZoom, function (c) {
        var startPos = c._latlng,
          markers = c._markers,
          m;

        if (!bounds.contains(startPos)) {
          startPos = null;
        }

        if (c._isSingleParent() && previousZoomLevel + 1 === newZoomLevel) { //Immediately add the new child and remove us
          fg.removeLayer(c);
          c._recursivelyAddChildrenToMap(null, newZoomLevel, bounds);
        } else {
          //Fade out old cluster
          c.clusterHide();
          c._recursivelyAddChildrenToMap(startPos, newZoomLevel, bounds);
        }

        //Remove all markers that aren't visible any more
        //TODO: Do we actually need to do this on the higher levels too?
        for (i = markers.length - 1; i >= 0; i--) {
          m = markers[i];
          if (!bounds.contains(m._latlng)) {
            fg.removeLayer(m);
          }
        }

      });

      this._forceLayout();

      //Update opacities
      this._topClusterLevel._recursivelyBecomeVisible(bounds, newZoomLevel);
      //TODO Maybe? Update markers in _recursivelyBecomeVisible
      fg.eachLayer(function (n) {
        if (!(n instanceof L.MarkerCluster) && n._icon) {
          n.clusterShow();
        }
      });

      //update the positions of the just added clusters/markers
      this._topClusterLevel._recursively(bounds, previousZoomLevel, newZoomLevel, function (c) {
        c._recursivelyRestoreChildPositions(newZoomLevel);
      });

      this._ignoreMove = false;

      //Remove the old clusters and close the zoom animation
      this._enqueue(function () {
        //update the positions of the just added clusters/markers
        this._topClusterLevel._recursively(bounds, previousZoomLevel, minZoom, function (c) {
          fg.removeLayer(c);
          c.clusterShow();
        });

        this._animationEnd();
      });
    },

    _animationZoomOut: function (previousZoomLevel, newZoomLevel) {
      this._animationZoomOutSingle(this._topClusterLevel, previousZoomLevel - 1, newZoomLevel);

      //Need to add markers for those that weren't on the map before but are now
      this._topClusterLevel._recursivelyAddChildrenToMap(null, newZoomLevel, this._getExpandedVisibleBounds());
      //Remove markers that were on the map before but won't be now
      this._topClusterLevel._recursivelyRemoveChildrenFromMap(this._currentShownBounds, Math.floor(this._map.getMinZoom()), previousZoomLevel, this._getExpandedVisibleBounds());
    },

    _animationAddLayer: function (layer, newCluster) {
      var me = this,
        fg = this._featureGroup;

      fg.addLayer(layer);
      if (newCluster !== layer) {
        if (newCluster._childCount > 2) { //Was already a cluster

          newCluster._updateIcon();
          this._forceLayout();
          this._animationStart();

          layer._setPos(this._map.latLngToLayerPoint(newCluster.getLatLng()));
          layer.clusterHide();

          this._enqueue(function () {
            fg.removeLayer(layer);
            layer.clusterShow();

            me._animationEnd();
          });

        } else { //Just became a cluster
          this._forceLayout();

          me._animationStart();
          me._animationZoomOutSingle(newCluster, this._map.getMaxZoom(), this._zoom);
        }
      }
    }
  },

  // Private methods for animated versions.
  _animationZoomOutSingle: function (cluster, previousZoomLevel, newZoomLevel) {
    var bounds = this._getExpandedVisibleBounds(),
      minZoom = Math.floor(this._map.getMinZoom());

    //Animate all of the markers in the clusters to move to their cluster center point
    cluster._recursivelyAnimateChildrenInAndAddSelfToMap(bounds, minZoom, previousZoomLevel + 1, newZoomLevel);

    var me = this;

    //Update the opacity (If we immediately set it they won't animate)
    this._forceLayout();
    cluster._recursivelyBecomeVisible(bounds, newZoomLevel);

    //TODO: Maybe use the transition timing stuff to make this more reliable
    //When the animations are done, tidy up
    this._enqueue(function () {

      //This cluster stopped being a cluster before the timeout fired
      if (cluster._childCount === 1) {
        var m = cluster._markers[0];
        //If we were in a cluster animation at the time then the opacity and position of our child could be wrong now, so fix it
        this._ignoreMove = true;
        m.setLatLng(m.getLatLng());
        this._ignoreMove = false;
        if (m.clusterShow) {
          m.clusterShow();
        }
      } else {
        cluster._recursively(bounds, newZoomLevel, minZoom, function (c) {
          c._recursivelyRemoveChildrenFromMap(bounds, minZoom, previousZoomLevel + 1);
        });
      }
      me._animationEnd();
    });
  },

  _animationEnd: function () {
    if (this._map) {
      this._map._mapPane.className = this._map._mapPane.className.replace(' leaflet-cluster-anim', '');
    }
    this._inZoomAnimation--;
    this.fire('animationend');
  },

  //Force a browser layout of stuff in the map
  // Should apply the current opacity and location to all elements so we can update them again for an animation
  _forceLayout: function () {
    //In my testing this works, infact offsetWidth of any element seems to work.
    //Could loop all this._layers and do this for each _icon if it stops working

    L.Util.falseFn(document.body.offsetWidth);
  }
});

L.markerClusterGroup = function (options) {
  return new L.MarkerClusterGroup(options);
};

var MarkerCluster = L.MarkerCluster = L.Marker.extend({
  options: L.Icon.prototype.options,

  initialize: function (group, zoom, a, b) {

    L.Marker.prototype.initialize.call(this, a ? (a._cLatLng || a.getLatLng()) : new L.LatLng(0, 0),
      {icon: this, pane: group.options.clusterPane});

    this._group = group;
    this._zoom = zoom;

    this._markers = [];
    this._childClusters = [];
    this._childCount = 0;
    this._iconNeedsUpdate = true;
    this._boundsNeedUpdate = true;

    this._bounds = new L.LatLngBounds();

    if (a) {
      this._addChild(a);
    }
    if (b) {
      this._addChild(b);
    }
  },

  //Recursively retrieve all child markers of this cluster
  getAllChildMarkers: function (storageArray) {
    storageArray = storageArray || [];

    for (var i = this._childClusters.length - 1; i >= 0; i--) {
      this._childClusters[i].getAllChildMarkers(storageArray);
    }

    for (var j = this._markers.length - 1; j >= 0; j--) {
      storageArray.push(this._markers[j]);
    }

    return storageArray;
  },

  //Returns the count of how many child markers we have
  getChildCount: function () {
    return this._childCount;
  },

  //Zoom to the minimum of showing all of the child markers, or the extents of this cluster
  zoomToBounds: function (fitBoundsOptions) {
    var childClusters = this._childClusters.slice(),
      map = this._group._map,
      boundsZoom = map.getBoundsZoom(this._bounds),
      zoom = this._zoom + 1,
      mapZoom = map.getZoom(),
      i;

    //calculate how far we need to zoom down to see all of the markers
    while (childClusters.length > 0 && boundsZoom > zoom) {
      zoom++;
      var newClusters = [];
      for (i = 0; i < childClusters.length; i++) {
        newClusters = newClusters.concat(childClusters[i]._childClusters);
      }
      childClusters = newClusters;
    }

    if (boundsZoom > zoom) {
      this._group._map.setView(this._latlng, zoom);
    } else if (boundsZoom <= mapZoom) { //If fitBounds wouldn't zoom us down, zoom us down instead
      this._group._map.setView(this._latlng, mapZoom + 1);
    } else {
      this._group._map.fitBounds(this._bounds, fitBoundsOptions);
    }
  },

  getBounds: function () {
    var bounds = new L.LatLngBounds();
    bounds.extend(this._bounds);
    return bounds;
  },

  _updateIcon: function () {
    this._iconNeedsUpdate = true;
    if (this._icon) {
      this.setIcon(this);
    }
  },

  //Cludge for Icon, we pretend to be an icon for performance
  createIcon: function () {
    if (this._iconNeedsUpdate) {
      this._iconObj = this._group.options.iconCreateFunction(this);
      this._iconNeedsUpdate = false;
    }
    return this._iconObj.createIcon();
  },
  createShadow: function () {
    return this._iconObj.createShadow();
  },


  _addChild: function (new1, isNotificationFromChild) {

    this._iconNeedsUpdate = true;

    this._boundsNeedUpdate = true;
    this._setClusterCenter(new1);

    if (new1 instanceof L.MarkerCluster) {
      if (!isNotificationFromChild) {
        this._childClusters.push(new1);
        new1.__parent = this;
      }
      this._childCount += new1._childCount;
    } else {
      if (!isNotificationFromChild) {
        this._markers.push(new1);
      }
      this._childCount++;
    }

    if (this.__parent) {
      this.__parent._addChild(new1, true);
    }
  },

  /**
   * Makes sure the cluster center is set. If not, uses the child center if it is a cluster, or the marker position.
   * @param child L.MarkerCluster|L.Marker that will be used as cluster center if not defined yet.
   * @private
   */
  _setClusterCenter: function (child) {
    if (!this._cLatLng) {
      // when clustering, take position of the first point as the cluster center
      this._cLatLng = child._cLatLng || child._latlng;
    }
  },

  /**
   * Assigns impossible bounding values so that the next extend entirely determines the new bounds.
   * This method avoids having to trash the previous L.LatLngBounds object and to create a new one, which is much slower for this class.
   * As long as the bounds are not extended, most other methods would probably fail, as they would with bounds initialized but not extended.
   * @private
   */
  _resetBounds: function () {
    var bounds = this._bounds;

    if (bounds._southWest) {
      bounds._southWest.lat = Infinity;
      bounds._southWest.lng = Infinity;
    }
    if (bounds._northEast) {
      bounds._northEast.lat = -Infinity;
      bounds._northEast.lng = -Infinity;
    }
  },

  _recalculateBounds: function () {
    var markers = this._markers,
      childClusters = this._childClusters,
      latSum = 0,
      lngSum = 0,
      totalCount = this._childCount,
      i, child, childLatLng, childCount;

    // Case where all markers are removed from the map and we are left with just an empty _topClusterLevel.
    if (totalCount === 0) {
      return;
    }

    // Reset rather than creating a new object, for performance.
    this._resetBounds();

    // Child markers.
    for (i = 0; i < markers.length; i++) {
      childLatLng = markers[i]._latlng;

      this._bounds.extend(childLatLng);

      latSum += childLatLng.lat;
      lngSum += childLatLng.lng;
    }

    // Child clusters.
    for (i = 0; i < childClusters.length; i++) {
      child = childClusters[i];

      // Re-compute child bounds and weighted position first if necessary.
      if (child._boundsNeedUpdate) {
        child._recalculateBounds();
      }

      this._bounds.extend(child._bounds);

      childLatLng = child._wLatLng;
      childCount = child._childCount;

      latSum += childLatLng.lat * childCount;
      lngSum += childLatLng.lng * childCount;
    }

    this._latlng = this._wLatLng = new L.LatLng(latSum / totalCount, lngSum / totalCount);

    // Reset dirty flag.
    this._boundsNeedUpdate = false;
  },

  //Set our markers position as given and add it to the map
  _addToMap: function (startPos) {
    if (startPos) {
      this._backupLatlng = this._latlng;
      this.setLatLng(startPos);
    }
    this._group._featureGroup.addLayer(this);
  },

  _recursivelyAnimateChildrenIn: function (bounds, center, maxZoom) {
    this._recursively(bounds, this._group._map.getMinZoom(), maxZoom - 1,
      function (c) {
        var markers = c._markers,
          i, m;
        for (i = markers.length - 1; i >= 0; i--) {
          m = markers[i];

          //Only do it if the icon is still on the map
          if (m._icon) {
            m._setPos(center);
            m.clusterHide();
          }
        }
      },
      function (c) {
        var childClusters = c._childClusters,
          j, cm;
        for (j = childClusters.length - 1; j >= 0; j--) {
          cm = childClusters[j];
          if (cm._icon) {
            cm._setPos(center);
            cm.clusterHide();
          }
        }
      }
    );
  },

  _recursivelyAnimateChildrenInAndAddSelfToMap: function (bounds, mapMinZoom, previousZoomLevel, newZoomLevel) {
    this._recursively(bounds, newZoomLevel, mapMinZoom,
      function (c) {
        c._recursivelyAnimateChildrenIn(bounds, c._group._map.latLngToLayerPoint(c.getLatLng()).round(), previousZoomLevel);

        //TODO: depthToAnimateIn affects _isSingleParent, if there is a multizoom we may/may not be.
        //As a hack we only do a animation free zoom on a single level zoom, if someone does multiple levels then we always animate
        if (c._isSingleParent() && previousZoomLevel - 1 === newZoomLevel) {
          c.clusterShow();
          c._recursivelyRemoveChildrenFromMap(bounds, mapMinZoom, previousZoomLevel); //Immediately remove our children as we are replacing them. TODO previousBounds not bounds
        } else {
          c.clusterHide();
        }

        c._addToMap();
      }
    );
  },

  _recursivelyBecomeVisible: function (bounds, zoomLevel) {
    this._recursively(bounds, this._group._map.getMinZoom(), zoomLevel, null, function (c) {
      c.clusterShow();
    });
  },

  _recursivelyAddChildrenToMap: function (startPos, zoomLevel, bounds) {
    this._recursively(bounds, this._group._map.getMinZoom() - 1, zoomLevel,
      function (c) {
        if (zoomLevel === c._zoom) {
          return;
        }

        //Add our child markers at startPos (so they can be animated out)
        for (var i = c._markers.length - 1; i >= 0; i--) {
          var nm = c._markers[i];

          if (!bounds.contains(nm._latlng)) {
            continue;
          }

          if (startPos) {
            nm._backupLatlng = nm.getLatLng();

            nm.setLatLng(startPos);
            if (nm.clusterHide) {
              nm.clusterHide();
            }
          }

          c._group._featureGroup.addLayer(nm);
        }
      },
      function (c) {
        c._addToMap(startPos);
      }
    );
  },

  _recursivelyRestoreChildPositions: function (zoomLevel) {
    //Fix positions of child markers
    for (var i = this._markers.length - 1; i >= 0; i--) {
      var nm = this._markers[i];
      if (nm._backupLatlng) {
        nm.setLatLng(nm._backupLatlng);
        delete nm._backupLatlng;
      }
    }

    if (zoomLevel - 1 === this._zoom) {
      //Reposition child clusters
      for (var j = this._childClusters.length - 1; j >= 0; j--) {
        this._childClusters[j]._restorePosition();
      }
    } else {
      for (var k = this._childClusters.length - 1; k >= 0; k--) {
        this._childClusters[k]._recursivelyRestoreChildPositions(zoomLevel);
      }
    }
  },

  _restorePosition: function () {
    if (this._backupLatlng) {
      this.setLatLng(this._backupLatlng);
      delete this._backupLatlng;
    }
  },

  //exceptBounds: If set, don't remove any markers/clusters in it
  _recursivelyRemoveChildrenFromMap: function (previousBounds, mapMinZoom, zoomLevel, exceptBounds) {
    var m, i;
    this._recursively(previousBounds, mapMinZoom - 1, zoomLevel - 1,
      function (c) {
        //Remove markers at every level
        for (i = c._markers.length - 1; i >= 0; i--) {
          m = c._markers[i];
          if (!exceptBounds || !exceptBounds.contains(m._latlng)) {
            c._group._featureGroup.removeLayer(m);
            if (m.clusterShow) {
              m.clusterShow();
            }
          }
        }
      },
      function (c) {
        //Remove child clusters at just the bottom level
        for (i = c._childClusters.length - 1; i >= 0; i--) {
          m = c._childClusters[i];
          if (!exceptBounds || !exceptBounds.contains(m._latlng)) {
            c._group._featureGroup.removeLayer(m);
            if (m.clusterShow) {
              m.clusterShow();
            }
          }
        }
      }
    );
  },

  //Run the given functions recursively to this and child clusters
  // boundsToApplyTo: a L.LatLngBounds representing the bounds of what clusters to recurse in to
  // zoomLevelToStart: zoom level to start running functions (inclusive)
  // zoomLevelToStop: zoom level to stop running functions (inclusive)
  // runAtEveryLevel: function that takes an L.MarkerCluster as an argument that should be applied on every level
  // runAtBottomLevel: function that takes an L.MarkerCluster as an argument that should be applied at only the bottom level
  _recursively: function (boundsToApplyTo, zoomLevelToStart, zoomLevelToStop, runAtEveryLevel, runAtBottomLevel) {
    var childClusters = this._childClusters,
      zoom = this._zoom,
      i, c;

    if (zoomLevelToStart <= zoom) {
      if (runAtEveryLevel) {
        runAtEveryLevel(this);
      }
      if (runAtBottomLevel && zoom === zoomLevelToStop) {
        runAtBottomLevel(this);
      }
    }

    if (zoom < zoomLevelToStart || zoom < zoomLevelToStop) {
      for (i = childClusters.length - 1; i >= 0; i--) {
        c = childClusters[i];
        if (boundsToApplyTo.intersects(c._bounds)) {
          c._recursively(boundsToApplyTo, zoomLevelToStart, zoomLevelToStop, runAtEveryLevel, runAtBottomLevel);
        }
      }
    }
  },

  //Returns true if we are the parent of only one cluster and that cluster is the same as us
  _isSingleParent: function () {
    //Don't need to check this._markers as the rest won't work if there are any
    return this._childClusters.length > 0 && this._childClusters[0]._childCount === this._childCount;
  }
});

/*
 * Extends L.Marker to include two extra methods: clusterHide and clusterShow.
 *
 * They work as setOpacity(0) and setOpacity(1) respectively, but
 * they will remember the marker's opacity when hiding and showing it again.
 *
 */


L.Marker.include({

  clusterHide: function () {
    this.options.opacityWhenUnclustered = this.options.opacity || 1;
    return this.setOpacity(0);
  },

  clusterShow: function () {
    var ret = this.setOpacity(this.options.opacity || this.options.opacityWhenUnclustered);
    delete this.options.opacityWhenUnclustered;
    return ret;
  }

});

L.DistanceGrid = function (cellSize) {
  this._cellSize = cellSize;
  this._sqCellSize = cellSize * cellSize;
  this._grid = {};
  this._objectPoint = {};
};

L.DistanceGrid.prototype = {

  addObject: function (obj, point) {
    var x = this._getCoord(point.x),
      y = this._getCoord(point.y),
      grid = this._grid,
      row = grid[y] = grid[y] || {},
      cell = row[x] = row[x] || [],
      stamp = L.Util.stamp(obj);

    this._objectPoint[stamp] = point;

    cell.push(obj);
  },

  updateObject: function (obj, point) {
    this.removeObject(obj);
    this.addObject(obj, point);
  },

  //Returns true if the object was found
  removeObject: function (obj, point) {
    var x = this._getCoord(point.x),
      y = this._getCoord(point.y),
      grid = this._grid,
      row = grid[y] = grid[y] || {},
      cell = row[x] = row[x] || [],
      i, len;

    delete this._objectPoint[L.Util.stamp(obj)];

    for (i = 0, len = cell.length; i < len; i++) {
      if (cell[i] === obj) {

        cell.splice(i, 1);

        if (len === 1) {
          delete row[x];
        }

        return true;
      }
    }

  },

  eachObject: function (fn, context) {
    var i, j, k, len, row, cell, removed,
      grid = this._grid;

    for (i in grid) {
      row = grid[i];

      for (j in row) {
        cell = row[j];

        for (k = 0, len = cell.length; k < len; k++) {
          removed = fn.call(context, cell[k]);
          if (removed) {
            k--;
            len--;
          }
        }
      }
    }
  },

  getNearObject: function (point) {
    var x = this._getCoord(point.x),
      y = this._getCoord(point.y),
      i, j, k, row, cell, len, obj, dist,
      objectPoint = this._objectPoint,
      closestDistSq = this._sqCellSize,
      closest = null;

    for (i = y - 1; i <= y + 1; i++) {
      row = this._grid[i];
      if (row) {

        for (j = x - 1; j <= x + 1; j++) {
          cell = row[j];
          if (cell) {

            for (k = 0, len = cell.length; k < len; k++) {
              obj = cell[k];
              dist = this._sqDist(objectPoint[L.Util.stamp(obj)], point);
              if (dist < closestDistSq ||
                dist <= closestDistSq && closest === null) {
                closestDistSq = dist;
                closest = obj;
              }
            }
          }
        }
      }
    }
    return closest;
  },

  _getCoord: function (x) {
    var coord = Math.floor(x / this._cellSize);
    return isFinite(coord) ? coord : x;
  },

  _sqDist: function (p, p2) {
    var dx = p2.x - p.x,
      dy = p2.y - p.y;
    return dx * dx + dy * dy;
  }
};

/* Copyright (c) 2012 the authors listed at the following URL, and/or
 the authors of referenced articles or incorporated external code:
 http://en.literateprograms.org/Quickhull_(Javascript)?action=history&offset=20120410175256

 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files (the
 "Software"), to deal in the Software without restriction, including
 without limitation the rights to use, copy, modify, merge, publish,
 distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to
 the following conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

 Retrieved from: http://en.literateprograms.org/Quickhull_(Javascript)?oldid=18434
 */

(function () {
  L.QuickHull = {

    /*
         * @param {Object} cpt a point to be measured from the baseline
         * @param {Array} bl the baseline, as represented by a two-element
         *   array of latlng objects.
         * @returns {Number} an approximate distance measure
         */
    getDistant: function (cpt, bl) {
      var vY = bl[1].lat - bl[0].lat,
        vX = bl[0].lng - bl[1].lng;
      return (vX * (cpt.lat - bl[0].lat) + vY * (cpt.lng - bl[0].lng));
    },

    /*
         * @param {Array} baseLine a two-element array of latlng objects
         *   representing the baseline to project from
         * @param {Array} latLngs an array of latlng objects
         * @returns {Object} the maximum point and all new points to stay
         *   in consideration for the hull.
         */
    findMostDistantPointFromBaseLine: function (baseLine, latLngs) {
      var maxD = 0,
        maxPt = null,
        newPoints = [],
        i, pt, d;

      for (i = latLngs.length - 1; i >= 0; i--) {
        pt = latLngs[i];
        d = this.getDistant(pt, baseLine);

        if (d > 0) {
          newPoints.push(pt);
        } else {
          continue;
        }

        if (d > maxD) {
          maxD = d;
          maxPt = pt;
        }
      }

      return {maxPoint: maxPt, newPoints: newPoints};
    },


    /*
         * Given a baseline, compute the convex hull of latLngs as an array
         * of latLngs.
         *
         * @param {Array} latLngs
         * @returns {Array}
         */
    buildConvexHull: function (baseLine, latLngs) {
      var convexHullBaseLines = [],
        t = this.findMostDistantPointFromBaseLine(baseLine, latLngs);

      if (t.maxPoint) { // if there is still a point "outside" the base line
        convexHullBaseLines =
          convexHullBaseLines.concat(
            this.buildConvexHull([baseLine[0], t.maxPoint], t.newPoints)
          );
        convexHullBaseLines =
          convexHullBaseLines.concat(
            this.buildConvexHull([t.maxPoint, baseLine[1]], t.newPoints)
          );
        return convexHullBaseLines;
      } else {  // if there is no more point "outside" the base line, the current base line is part of the convex hull
        return [baseLine[0]];
      }
    },

    /*
         * Given an array of latlngs, compute a convex hull as an array
         * of latlngs
         *
         * @param {Array} latLngs
         * @returns {Array}
         */
    getConvexHull: function (latLngs) {
      // find first baseline
      var maxLat = false, minLat = false,
        maxLng = false, minLng = false,
        maxLatPt = null, minLatPt = null,
        maxLngPt = null, minLngPt = null,
        maxPt = null, minPt = null,
        i;

      for (i = latLngs.length - 1; i >= 0; i--) {
        var pt = latLngs[i];
        if (maxLat === false || pt.lat > maxLat) {
          maxLatPt = pt;
          maxLat = pt.lat;
        }
        if (minLat === false || pt.lat < minLat) {
          minLatPt = pt;
          minLat = pt.lat;
        }
        if (maxLng === false || pt.lng > maxLng) {
          maxLngPt = pt;
          maxLng = pt.lng;
        }
        if (minLng === false || pt.lng < minLng) {
          minLngPt = pt;
          minLng = pt.lng;
        }
      }

      if (minLat !== maxLat) {
        minPt = minLatPt;
        maxPt = maxLatPt;
      } else {
        minPt = minLngPt;
        maxPt = maxLngPt;
      }

      var ch = [].concat(this.buildConvexHull([minPt, maxPt], latLngs),
        this.buildConvexHull([maxPt, minPt], latLngs));
      return ch;
    }
  };
}());

L.MarkerCluster.include({
  getConvexHull: function () {
    var childMarkers = this.getAllChildMarkers(),
      points = [],
      p, i;

    for (i = childMarkers.length - 1; i >= 0; i--) {
      p = childMarkers[i].getLatLng();
      points.push(p);
    }

    return L.QuickHull.getConvexHull(points);
  }
});

//This code is 100% based on https://github.com/jawj/OverlappingMarkerSpiderfier-Leaflet
//Huge thanks to jawj for implementing it first to make my job easy :-)

L.MarkerCluster.include({

  _2PI: Math.PI * 2,
  _circleFootSeparation: 25, //related to circumference of circle
  _circleStartAngle: 0,

  _spiralFootSeparation: 28, //related to size of spiral (experiment!)
  _spiralLengthStart: 11,
  _spiralLengthFactor: 5,

  _circleSpiralSwitchover: 9, //show spiral instead of circle from this marker count upwards.
  // 0 -> always spiral; Infinity -> always circle

  spiderfy: function () {
    if (this._group._spiderfied === this || this._group._inZoomAnimation) {
      return;
    }

    var childMarkers = this.getAllChildMarkers(),
      group = this._group,
      map = group._map,
      center = map.latLngToLayerPoint(this._latlng),
      positions;

    this._group._unspiderfy();
    this._group._spiderfied = this;

    //TODO Maybe: childMarkers order by distance to center

    if (childMarkers.length >= this._circleSpiralSwitchover) {
      positions = this._generatePointsSpiral(childMarkers.length, center);
    } else {
      center.y += 10; // Otherwise circles look wrong => hack for standard blue icon, renders differently for other icons.
      positions = this._generatePointsCircle(childMarkers.length, center);
    }

    this._animationSpiderfy(childMarkers, positions);
  },

  unspiderfy: function (zoomDetails) {
    /// <param Name="zoomDetails">Argument from zoomanim if being called in a zoom animation or null otherwise</param>
    if (this._group._inZoomAnimation) {
      return;
    }
    this._animationUnspiderfy(zoomDetails);

    this._group._spiderfied = null;
  },

  _generatePointsCircle: function (count, centerPt) {
    var circumference = this._group.options.spiderfyDistanceMultiplier * this._circleFootSeparation * (2 + count),
      legLength = circumference / this._2PI,  //radius from circumference
      angleStep = this._2PI / count,
      res = [],
      i, angle;

    legLength = Math.max(legLength, 35); // Minimum distance to get outside the cluster icon.

    res.length = count;

    for (i = 0; i < count; i++) { // Clockwise, like spiral.
      angle = this._circleStartAngle + i * angleStep;
      res[i] = new L.Point(centerPt.x + legLength * Math.cos(angle), centerPt.y + legLength * Math.sin(angle))._round();
    }

    return res;
  },

  _generatePointsSpiral: function (count, centerPt) {
    var spiderfyDistanceMultiplier = this._group.options.spiderfyDistanceMultiplier,
      legLength = spiderfyDistanceMultiplier * this._spiralLengthStart,
      separation = spiderfyDistanceMultiplier * this._spiralFootSeparation,
      lengthFactor = spiderfyDistanceMultiplier * this._spiralLengthFactor * this._2PI,
      angle = 0,
      res = [],
      i;

    res.length = count;

    // Higher index, closer position to cluster center.
    for (i = count; i >= 0; i--) {
      // Skip the first position, so that we are already farther from center and we avoid
      // being under the default cluster icon (especially important for Circle Markers).
      if (i < count) {
        res[i] = new L.Point(centerPt.x + legLength * Math.cos(angle), centerPt.y + legLength * Math.sin(angle))._round();
      }
      angle += separation / legLength + i * 0.0005;
      legLength += lengthFactor / angle;
    }
    return res;
  },

  _noanimationUnspiderfy: function () {
    var group = this._group,
      map = group._map,
      fg = group._featureGroup,
      childMarkers = this.getAllChildMarkers(),
      m, i;

    group._ignoreMove = true;

    this.setOpacity(1);
    for (i = childMarkers.length - 1; i >= 0; i--) {
      m = childMarkers[i];

      fg.removeLayer(m);

      if (m._preSpiderfyLatlng) {
        m.setLatLng(m._preSpiderfyLatlng);
        delete m._preSpiderfyLatlng;
      }
      if (m.setZIndexOffset) {
        m.setZIndexOffset(0);
      }

      if (m._spiderLeg) {
        map.removeLayer(m._spiderLeg);
        delete m._spiderLeg;
      }
    }

    group.fire('unspiderfied', {
      cluster: this,
      markers: childMarkers
    });
    group._ignoreMove = false;
    group._spiderfied = null;
  }
});

//Non Animated versions of everything
L.MarkerClusterNonAnimated = L.MarkerCluster.extend({
  _animationSpiderfy: function (childMarkers, positions) {
    var group = this._group,
      map = group._map,
      fg = group._featureGroup,
      legOptions = this._group.options.spiderLegPolylineOptions,
      i, m, leg, newPos;

    group._ignoreMove = true;

    // Traverse in ascending order to make sure that inner circleMarkers are on top of further legs. Normal markers are re-ordered by newPosition.
    // The reverse order trick no longer improves performance on modern browsers.
    for (i = 0; i < childMarkers.length; i++) {
      newPos = map.layerPointToLatLng(positions[i]);
      m = childMarkers[i];

      // Add the leg before the marker, so that in case the latter is a circleMarker, the leg is behind it.
      leg = new L.Polyline([this._latlng, newPos], legOptions);
      map.addLayer(leg);
      m._spiderLeg = leg;

      // Now add the marker.
      m._preSpiderfyLatlng = m._latlng;
      m.setLatLng(newPos);
      if (m.setZIndexOffset) {
        m.setZIndexOffset(1000000); //Make these appear on top of EVERYTHING
      }

      fg.addLayer(m);
    }
    this.setOpacity(0.3);

    group._ignoreMove = false;
    group.fire('spiderfied', {
      cluster: this,
      markers: childMarkers
    });
  },

  _animationUnspiderfy: function () {
    this._noanimationUnspiderfy();
  }
});

//Animated versions here
L.MarkerCluster.include({

  _animationSpiderfy: function (childMarkers, positions) {
    var me = this,
      group = this._group,
      map = group._map,
      fg = group._featureGroup,
      thisLayerLatLng = this._latlng,
      thisLayerPos = map.latLngToLayerPoint(thisLayerLatLng),
      svg = L.Path.SVG,
      legOptions = L.extend({}, this._group.options.spiderLegPolylineOptions), // Copy the options so that we can modify them for animation.
      finalLegOpacity = legOptions.opacity,
      i, m, leg, legPath, legLength, newPos;

    if (finalLegOpacity === undefined) {
      finalLegOpacity = L.MarkerClusterGroup.prototype.options.spiderLegPolylineOptions.opacity;
    }

    if (svg) {
      // If the initial opacity of the spider leg is not 0 then it appears before the animation starts.
      legOptions.opacity = 0;

      // Add the class for CSS transitions.
      legOptions.className = (legOptions.className || '') + ' leaflet-cluster-spider-leg';
    } else {
      // Make sure we have a defined opacity.
      legOptions.opacity = finalLegOpacity;
    }

    group._ignoreMove = true;

    // Add markers and spider legs to map, hidden at our center point.
    // Traverse in ascending order to make sure that inner circleMarkers are on top of further legs. Normal markers are re-ordered by newPosition.
    // The reverse order trick no longer improves performance on modern browsers.
    for (i = 0; i < childMarkers.length; i++) {
      m = childMarkers[i];

      newPos = map.layerPointToLatLng(positions[i]);

      // Add the leg before the marker, so that in case the latter is a circleMarker, the leg is behind it.
      leg = new L.Polyline([thisLayerLatLng, newPos], legOptions);
      map.addLayer(leg);
      m._spiderLeg = leg;

      // Explanations: https://jakearchibald.com/2013/animated-line-drawing-svg/
      // In our case the transition property is declared in the CSS file.
      if (svg) {
        legPath = leg._path;
        legLength = legPath.getTotalLength() + 0.1; // Need a small extra length to avoid remaining dot in Firefox.
        legPath.style.strokeDasharray = legLength; // Just 1 length is enough, it will be duplicated.
        legPath.style.strokeDashoffset = legLength;
      }

      // If it is a marker, add it now and we'll animate it out
      if (m.setZIndexOffset) {
        m.setZIndexOffset(1000000); // Make normal markers appear on top of EVERYTHING
      }
      if (m.clusterHide) {
        m.clusterHide();
      }

      // Vectors just get immediately added
      fg.addLayer(m);

      if (m._setPos) {
        m._setPos(thisLayerPos);
      }
    }

    group._forceLayout();
    group._animationStart();

    // Reveal markers and spider legs.
    for (i = childMarkers.length - 1; i >= 0; i--) {
      newPos = map.layerPointToLatLng(positions[i]);
      m = childMarkers[i];

      //Move marker to new position
      m._preSpiderfyLatlng = m._latlng;
      m.setLatLng(newPos);

      if (m.clusterShow) {
        m.clusterShow();
      }

      // Animate leg (animation is actually delegated to CSS transition).
      if (svg) {
        leg = m._spiderLeg;
        legPath = leg._path;
        legPath.style.strokeDashoffset = 0;
        //legPath.style.strokeOpacity = finalLegOpacity;
        leg.setStyle({opacity: finalLegOpacity});
      }
    }
    this.setOpacity(0.3);

    group._ignoreMove = false;

    setTimeout(function () {
      group._animationEnd();
      group.fire('spiderfied', {
        cluster: me,
        markers: childMarkers
      });
    }, 200);
  },

  _animationUnspiderfy: function (zoomDetails) {
    var me = this,
      group = this._group,
      map = group._map,
      fg = group._featureGroup,
      thisLayerPos = zoomDetails ? map._latLngToNewLayerPoint(this._latlng, zoomDetails.zoom, zoomDetails.center) : map.latLngToLayerPoint(this._latlng),
      childMarkers = this.getAllChildMarkers(),
      svg = L.Path.SVG,
      m, i, leg, legPath, legLength, nonAnimatable;

    group._ignoreMove = true;
    group._animationStart();

    //Make us visible and bring the child markers back in
    this.setOpacity(1);
    for (i = childMarkers.length - 1; i >= 0; i--) {
      m = childMarkers[i];

      //Marker was added to us after we were spiderfied
      if (!m._preSpiderfyLatlng) {
        continue;
      }

      //Close any popup on the marker first, otherwise setting the location of the marker will make the map scroll
      m.closePopup();

      //Fix up the location to the real one
      m.setLatLng(m._preSpiderfyLatlng);
      delete m._preSpiderfyLatlng;

      //Hack override the location to be our center
      nonAnimatable = true;
      if (m._setPos) {
        m._setPos(thisLayerPos);
        nonAnimatable = false;
      }
      if (m.clusterHide) {
        m.clusterHide();
        nonAnimatable = false;
      }
      if (nonAnimatable) {
        fg.removeLayer(m);
      }

      // Animate the spider leg back in (animation is actually delegated to CSS transition).
      if (svg) {
        leg = m._spiderLeg;
        legPath = leg._path;
        legLength = legPath.getTotalLength() + 0.1;
        legPath.style.strokeDashoffset = legLength;
        leg.setStyle({opacity: 0});
      }
    }

    group._ignoreMove = false;

    setTimeout(function () {
      //If we have only <= one child left then that marker will be shown on the map so don't remove it!
      var stillThereChildCount = 0;
      for (i = childMarkers.length - 1; i >= 0; i--) {
        m = childMarkers[i];
        if (m._spiderLeg) {
          stillThereChildCount++;
        }
      }


      for (i = childMarkers.length - 1; i >= 0; i--) {
        m = childMarkers[i];

        if (!m._spiderLeg) { //Has already been unspiderfied
          continue;
        }

        if (m.clusterShow) {
          m.clusterShow();
        }
        if (m.setZIndexOffset) {
          m.setZIndexOffset(0);
        }

        if (stillThereChildCount > 1) {
          fg.removeLayer(m);
        }

        map.removeLayer(m._spiderLeg);
        delete m._spiderLeg;
      }
      group._animationEnd();
      group.fire('unspiderfied', {
        cluster: me,
        markers: childMarkers
      });
    }, 200);
  }
});


L.MarkerClusterGroup.include({
  //The MarkerCluster currently spiderfied (if any)
  _spiderfied: null,

  unspiderfy: function () {
    this._unspiderfy.apply(this, arguments);
  },

  _spiderfierOnAdd: function () {
    this._map.on('click', this._unspiderfyWrapper, this);

    if (this._map.options.zoomAnimation) {
      this._map.on('zoomstart', this._unspiderfyZoomStart, this);
    }
    //Browsers without zoomAnimation or a big zoom don't fire zoomstart
    this._map.on('zoomend', this._noanimationUnspiderfy, this);

    if (!L.Browser.touch) {
      this._map.getRenderer(this);
      //Needs to happen in the pageload, not after, or animations don't work in webkit
      //  http://stackoverflow.com/questions/8455200/svg-animate-with-dynamically-added-elements
      //Disable on touch browsers as the animation messes up on a touch zoom and isn't very noticable
    }
  },

  _spiderfierOnRemove: function () {
    this._map.off('click', this._unspiderfyWrapper, this);
    this._map.off('zoomstart', this._unspiderfyZoomStart, this);
    this._map.off('zoomanim', this._unspiderfyZoomAnim, this);
    this._map.off('zoomend', this._noanimationUnspiderfy, this);

    //Ensure that markers are back where they should be
    // Use no animation to avoid a sticky leaflet-cluster-anim class on mapPane
    this._noanimationUnspiderfy();
  },

  //On zoom start we add a zoomanim handler so that we are guaranteed to be last (after markers are animated)
  //This means we can define the animation they do rather than Markers doing an animation to their actual location
  _unspiderfyZoomStart: function () {
    if (!this._map) { //May have been removed from the map by a zoomEnd handler
      return;
    }

    this._map.on('zoomanim', this._unspiderfyZoomAnim, this);
  },

  _unspiderfyZoomAnim: function (zoomDetails) {
    //Wait until the first zoomanim after the user has finished touch-zooming before running the animation
    if (L.DomUtil.hasClass(this._map._mapPane, 'leaflet-touching')) {
      return;
    }

    this._map.off('zoomanim', this._unspiderfyZoomAnim, this);
    this._unspiderfy(zoomDetails);
  },

  _unspiderfyWrapper: function () {
    /// <summary>_unspiderfy but passes no arguments</summary>
    this._unspiderfy();
  },

  _unspiderfy: function (zoomDetails) {
    if (this._spiderfied) {
      this._spiderfied.unspiderfy(zoomDetails);
    }
  },

  _noanimationUnspiderfy: function () {
    if (this._spiderfied) {
      this._spiderfied._noanimationUnspiderfy();
    }
  },

  //If the given layer is currently being spiderfied then we unspiderfy it so it isn't on the map anymore etc
  _unspiderfyLayer: function (layer) {
    if (layer._spiderLeg) {
      this._featureGroup.removeLayer(layer);

      if (layer.clusterShow) {
        layer.clusterShow();
      }
      //Position will be fixed up immediately in _animationUnspiderfy
      if (layer.setZIndexOffset) {
        layer.setZIndexOffset(0);
      }

      this._map.removeLayer(layer._spiderLeg);
      delete layer._spiderLeg;
    }
  }
});

/**
 * Adds 1 public method to MCG and 1 to L.Marker to facilitate changing
 * markers' icon options and refreshing their icon and their parent clusters
 * accordingly (case where their iconCreateFunction uses data of childMarkers
 * to make up the cluster icon).
 */


L.MarkerClusterGroup.include({
  /**
   * Updates the icon of all clusters which are parents of the given marker(s).
   * In singleMarkerMode, also updates the given marker(s) icon.
   * @param layers L.MarkerClusterGroup|L.LayerGroup|Array(L.Marker)|Map(L.Marker)|
   * L.MarkerCluster|L.Marker (optional) list of markers (or single marker) whose parent
   * clusters need to be updated. If not provided, retrieves all child markers of this.
   * @returns {L.MarkerClusterGroup}
   */
  refreshClusters: function (layers) {
    if (!layers) {
      layers = this._topClusterLevel.getAllChildMarkers();
    } else if (layers instanceof L.MarkerClusterGroup) {
      layers = layers._topClusterLevel.getAllChildMarkers();
    } else if (layers instanceof L.LayerGroup) {
      layers = layers._layers;
    } else if (layers instanceof L.MarkerCluster) {
      layers = layers.getAllChildMarkers();
    } else if (layers instanceof L.Marker) {
      layers = [layers];
    } // else: must be an Array(L.Marker)|Map(L.Marker)
    this._flagParentsIconsNeedUpdate(layers);
    this._refreshClustersIcons();

    // In case of singleMarkerMode, also re-draw the markers.
    if (this.options.singleMarkerMode) {
      this._refreshSingleMarkerModeMarkers(layers);
    }

    return this;
  },

  /**
   * Simply flags all parent clusters of the given markers as having a "dirty" icon.
   * @param layers Array(L.Marker)|Map(L.Marker) list of markers.
   * @private
   */
  _flagParentsIconsNeedUpdate: function (layers) {
    var id, parent;

    // Assumes layers is an Array or an Object whose prototype is non-enumerable.
    for (id in layers) {
      // Flag parent clusters' icon as "dirty", all the way up.
      // Dumb process that flags multiple times upper parents, but still
      // much more efficient than trying to be smart and make short lists,
      // at least in the case of a hierarchy following a power law:
      // http://jsperf.com/flag-nodes-in-power-hierarchy/2
      parent = layers[id].__parent;
      while (parent) {
        parent._iconNeedsUpdate = true;
        parent = parent.__parent;
      }
    }
  },

  /**
   * Re-draws the icon of the supplied markers.
   * To be used in singleMarkerMode only.
   * @param layers Array(L.Marker)|Map(L.Marker) list of markers.
   * @private
   */
  _refreshSingleMarkerModeMarkers: function (layers) {
    var id, layer;

    for (id in layers) {
      layer = layers[id];

      // Make sure we do not override markers that do not belong to THIS group.
      if (this.hasLayer(layer)) {
        // Need to re-create the icon first, then re-draw the marker.
        layer.setIcon(this._overrideMarkerIcon(layer));
      }
    }
  }
});

L.Marker.include({
  /**
   * Updates the given options in the marker's icon and refreshes the marker.
   * @param options map object of icon options.
   * @param directlyRefreshClusters boolean (optional) true to trigger
   * MCG.refreshClustersOf() right away with this single marker.
   * @returns {L.Marker}
   */
  refreshIconOptions: function (options, directlyRefreshClusters) {
    var icon = this.options.icon;

    L.setOptions(icon, options);

    this.setIcon(icon);

    // Shortcut to refresh the associated MCG clusters right away.
    // To be used when refreshing a single marker.
    // Otherwise, better use MCG.refreshClusters() once at the end with
    // the list of modified markers.
    if (directlyRefreshClusters && this.__parent) {
      this.__parent._group.refreshClusters(this);
    }

    return this;
  }
});
L.markerClusterGroup = function (options) {
  return new L.MarkerClusterGroup(options);
}
L.Control.MiniMap = L.Control.extend({

  includes: L.Evented ? L.Evented.prototype : L.Mixin.Events,

  options: {
    position: 'bottomright',
    toggleDisplay: false,
    zoomLevelOffset: -5,
    zoomLevelFixed: false,
    centerFixed: false,
    zoomAnimation: false,
    autoToggleDisplay: false,
    minimized: false,
    width: 150,
    height: 150,
    collapsedWidth: 19,
    collapsedHeight: 19,
    aimingRectOptions: {color: '#ff7800', weight: 1, clickable: false},
    shadowRectOptions: {color: '#000000', weight: 1, clickable: false, opacity: 0, fillOpacity: 0},
    strings: {hideText: 'Hide MiniMap', showText: 'Show MiniMap'},
    mapOptions: {}  // Allows definition / override of Leaflet map options.
  },

  // layer is the map layer to be shown in the minimap
  initialize: function (layer, options) {
    L.Util.setOptions(this, options);
    // Make sure the aiming rects are non-clickable even if the user tries to set them clickable (most likely by forgetting to specify them false)
    this.options.aimingRectOptions.clickable = false;
    this.options.shadowRectOptions.clickable = false;
    this._layer = layer;
  },

  onAdd: function (map) {

    this._mainMap = map;

    // Creating the container and stopping events from spilling through to the main map.
    this._container = L.DomUtil.create('div', 'leaflet-control-minimap');
    this._container.style.width = this.options.width + 'px';
    this._container.style.height = this.options.height + 'px';
    L.DomEvent.disableClickPropagation(this._container);
    L.DomEvent.on(this._container, 'mousewheel', L.DomEvent.stopPropagation);

    var mapOptions = {
      attributionControl: false,
      dragging: !this.options.centerFixed,
      zoomControl: false,
      zoomAnimation: this.options.zoomAnimation,
      autoToggleDisplay: this.options.autoToggleDisplay,
      touchZoom: this.options.centerFixed ? 'center' : !this._isZoomLevelFixed(),
      scrollWheelZoom: this.options.centerFixed ? 'center' : !this._isZoomLevelFixed(),
      doubleClickZoom: this.options.centerFixed ? 'center' : !this._isZoomLevelFixed(),
      boxZoom: !this._isZoomLevelFixed(),
      crs: map.options.crs
    };
    mapOptions = L.Util.extend(this.options.mapOptions, mapOptions);  // merge with priority of the local mapOptions object.

    this._miniMap = new L.Map(this._container, mapOptions);

    this._miniMap.addLayer(this._layer);

    // These bools are used to prevent infinite loops of the two maps notifying each other that they've moved.
    this._mainMapMoving = false;
    this._miniMapMoving = false;

    // Keep a record of this to prevent auto toggling when the user explicitly doesn't want it.
    this._userToggledDisplay = false;
    this._minimized = false;

    if (this.options.toggleDisplay) {
      this._addToggleButton();
    }

    this._miniMap.whenReady(L.Util.bind(function () {
      this._aimingRect = L.rectangle(this._mainMap.getBounds(), this.options.aimingRectOptions).addTo(this._miniMap);
      this._shadowRect = L.rectangle(this._mainMap.getBounds(), this.options.shadowRectOptions).addTo(this._miniMap);
      this._mainMap.on('moveend', this._onMainMapMoved, this);
      this._mainMap.on('move', this._onMainMapMoving, this);
      this._miniMap.on('movestart', this._onMiniMapMoveStarted, this);
      this._miniMap.on('move', this._onMiniMapMoving, this);
      this._miniMap.on('moveend', this._onMiniMapMoved, this);
    }, this));

    return this._container;
  },

  addTo: function (map) {
    L.Control.prototype.addTo.call(this, map);

    var center = this.options.centerFixed || this._mainMap.getCenter();
    this._miniMap.setView(center, this._decideZoom(true));
    this._setDisplay(this.options.minimized);
    return this;
  },

  onRemove: function (map) {
    this._mainMap.off('moveend', this._onMainMapMoved, this);
    this._mainMap.off('move', this._onMainMapMoving, this);
    this._miniMap.off('moveend', this._onMiniMapMoved, this);

    this._miniMap.removeLayer(this._layer);
  },

  changeLayer: function (layer) {
    this._miniMap.removeLayer(this._layer);
    this._layer = layer;
    this._miniMap.addLayer(this._layer);
  },

  _addToggleButton: function () {
    this._toggleDisplayButton = this.options.toggleDisplay ? this._createButton(
      '', this._toggleButtonInitialTitleText(), ('leaflet-control-minimap-toggle-display leaflet-control-minimap-toggle-display-' +
        this.options.position), this._container, this._toggleDisplayButtonClicked, this) : undefined;

    this._toggleDisplayButton.style.width = this.options.collapsedWidth + 'px';
    this._toggleDisplayButton.style.height = this.options.collapsedHeight + 'px';
  },

  _toggleButtonInitialTitleText: function () {
    if (this.options.minimized) {
      return this.options.strings.showText;
    } else {
      return this.options.strings.hideText;
    }
  },

  _createButton: function (html, title, className, container, fn, context) {
    var link = L.DomUtil.create('a', className, container);
    link.innerHTML = html;
    link.href = '#';
    link.title = title;

    var stop = L.DomEvent.stopPropagation;

    L.DomEvent
      .on(link, 'click', stop)
      .on(link, 'mousedown', stop)
      .on(link, 'dblclick', stop)
      .on(link, 'click', L.DomEvent.preventDefault)
      .on(link, 'click', fn, context);

    return link;
  },

  _toggleDisplayButtonClicked: function () {
    this._userToggledDisplay = true;
    if (!this._minimized) {
      this._minimize();
    } else {
      this._restore();
    }
  },

  _setDisplay: function (minimize) {
    if (minimize !== this._minimized) {
      if (!this._minimized) {
        this._minimize();
      } else {
        this._restore();
      }
    }
  },

  _minimize: function () {
    // hide the minimap
    if (this.options.toggleDisplay) {
      this._container.style.width = this.options.collapsedWidth + 'px';
      this._container.style.height = this.options.collapsedHeight + 'px';
      this._toggleDisplayButton.className += (' minimized-' + this.options.position);
      this._toggleDisplayButton.title = this.options.strings.showText;
    } else {
      this._container.style.display = 'none';
    }
    this._minimized = true;
    this._onToggle();
  },

  _restore: function () {
    if (this.options.toggleDisplay) {
      this._container.style.width = this.options.width + 'px';
      this._container.style.height = this.options.height + 'px';
      this._toggleDisplayButton.className = this._toggleDisplayButton.className
        .replace('minimized-' + this.options.position, '');
      this._toggleDisplayButton.title = this.options.strings.hideText;
    } else {
      this._container.style.display = 'block';
    }
    this._minimized = false;
    this._onToggle();
  },

  _onMainMapMoved: function (e) {
    if (!this._miniMapMoving) {
      var center = this.options.centerFixed || this._mainMap.getCenter();

      this._mainMapMoving = true;
      this._miniMap.setView(center, this._decideZoom(true));
      this._setDisplay(this._decideMinimized());
    } else {
      this._miniMapMoving = false;
    }
    this._aimingRect.setBounds(this._mainMap.getBounds());
  },

  _onMainMapMoving: function (e) {
    this._aimingRect.setBounds(this._mainMap.getBounds());
  },

  _onMiniMapMoveStarted: function (e) {
    if (!this.options.centerFixed) {
      var lastAimingRect = this._aimingRect.getBounds();
      var sw = this._miniMap.latLngToContainerPoint(lastAimingRect.getSouthWest());
      var ne = this._miniMap.latLngToContainerPoint(lastAimingRect.getNorthEast());
      this._lastAimingRectPosition = {sw: sw, ne: ne};
    }
  },

  _onMiniMapMoving: function (e) {
    if (!this.options.centerFixed) {
      if (!this._mainMapMoving && this._lastAimingRectPosition) {
        this._shadowRect.setBounds(new L.LatLngBounds(this._miniMap.containerPointToLatLng(this._lastAimingRectPosition.sw), this._miniMap.containerPointToLatLng(this._lastAimingRectPosition.ne)));
        this._shadowRect.setStyle({opacity: 1, fillOpacity: 0.3});
      }
    }
  },

  _onMiniMapMoved: function (e) {
    if (!this._mainMapMoving) {
      this._miniMapMoving = true;
      this._mainMap.setView(this._miniMap.getCenter(), this._decideZoom(false));
      this._shadowRect.setStyle({opacity: 0, fillOpacity: 0});
    } else {
      this._mainMapMoving = false;
    }
  },

  _isZoomLevelFixed: function () {
    var zoomLevelFixed = this.options.zoomLevelFixed;
    return this._isDefined(zoomLevelFixed) && this._isInteger(zoomLevelFixed);
  },

  _decideZoom: function (fromMaintoMini) {
    if (!this._isZoomLevelFixed()) {
      if (fromMaintoMini) {
        return this._mainMap.getZoom() + this.options.zoomLevelOffset;
      } else {
        var currentDiff = this._miniMap.getZoom() - this._mainMap.getZoom();
        var proposedZoom = this._miniMap.getZoom() - this.options.zoomLevelOffset;
        var toRet;

        if (currentDiff > this.options.zoomLevelOffset && this._mainMap.getZoom() < this._miniMap.getMinZoom() - this.options.zoomLevelOffset) {
          // This means the miniMap is zoomed out to the minimum zoom level and can't zoom any more.
          if (this._miniMap.getZoom() > this._lastMiniMapZoom) {
            // This means the user is trying to zoom in by using the minimap, zoom the main map.
            toRet = this._mainMap.getZoom() + 1;
            // Also we cheat and zoom the minimap out again to keep it visually consistent.
            this._miniMap.setZoom(this._miniMap.getZoom() - 1);
          } else {
            // Either the user is trying to zoom out past the mini map's min zoom or has just panned using it, we can't tell the difference.
            // Therefore, we ignore it!
            toRet = this._mainMap.getZoom();
          }
        } else {
          // This is what happens in the majority of cases, and always if you configure the min levels + offset in a sane fashion.
          toRet = proposedZoom;
        }
        this._lastMiniMapZoom = this._miniMap.getZoom();
        return toRet;
      }
    } else {
      if (fromMaintoMini) {
        return this.options.zoomLevelFixed;
      } else {
        return this._mainMap.getZoom();
      }
    }
  },

  _decideMinimized: function () {
    if (this._userToggledDisplay) {
      return this._minimized;
    }

    if (this.options.autoToggleDisplay) {
      if (this._mainMap.getBounds().contains(this._miniMap.getBounds())) {
        return true;
      }
      return false;
    }

    return this._minimized;
  },

  _isInteger: function (value) {
    return typeof value === 'number';
  },

  _isDefined: function (value) {
    return typeof value !== 'undefined';
  },

  _onToggle: function () {
    L.Util.requestAnimFrame(function () {
      L.DomEvent.on(this._container, 'transitionend', this._fireToggleEvents, this);
      if (!L.Browser.any3d) {
        L.Util.requestAnimFrame(this._fireToggleEvents, this);
      }
    }, this);
  },

  _fireToggleEvents: function () {
    L.DomEvent.off(this._container, 'transitionend', this._fireToggleEvents, this);
    var data = {minimized: this._minimized};
    this.fire(this._minimized ? 'minimize' : 'restore', data);
    this.fire('toggle', data);
  }
});

L.control.miniMap = function (layer, options) {
  return new L.Control.MiniMap(layer, options);
};
L.NsnMap = L.Class.extend({
  defaults: {
    attributionControl: false,
    homeControl: false,
    fullscreenControl: false,
    loadingControl: false,
    layersControl: false,
    miniMapControl: false
  },
  /**
   * 初始化方法
   */
  initialize: function (id, options) {
    if (options) {
      var opts = L.extend(this.defaults, options.map);
      this._map = L.map(id, opts);
      var miniLayer;
      //根据配置的图层信息，增加对应的图层
      if (options.layers) {
        var baseMaps = {},
          overlayMaps = {};
        //循环增加配置的图层数据，用于处理底图
        for (var i = 0, layer; layer = options.layers[i++];) {
          if (layer.type == 'wms-services') {
            var geoUrl = layer.url;
            $.ajax({
              url: geoUrl + '/ows?service=wms&version=1.3.0&request=GetCapabilities',
              type: 'get',
              dataType: 'xml',
              async: false
            }).done(function (el) {
              var layers = $(el).find('Layer').find('Layer');
              for (var i = 0, lay; lay = layers[i++];) {
                var layName = $(lay).children('Name').text();
                var title = $(lay).children('Title').text();
                if (layName) {
                  var wsp = layName.split(':')[0];
                  var _layer = this.newLayer({
                    url: geoUrl + '/' + wsp + '/wms',
                    layers: layName,
                    type: 'wms'
                  });
                  overlayMaps[title || layName] = _layer;
                }
              }
            });
          } else {
            var _layer = this.newLayer(layer);
            if (layer.isBaseLayer) {
              baseMaps[layer.name] = _layer;
              if (opts.miniMapControl && layer.show) {
                miniLayer = this.newLayer(layer);
              }
            } else {
              overlayMaps[layer.name] = _layer;
            }
            if (layer.show) {
              _layer.addTo(this._map);
            }
          }
        }
      }
      //增加必要组件,比例尺控件
      this._map.addControl(L.control.scale({
        imperial: false
      }));
      if (opts.layersControl) {
        this._layerCtrl = L.control.layers(baseMaps, overlayMaps).addTo(this._map);
      }
      if (opts.miniMapControl && miniLayer) {
        this.miniMapControl = L.control.miniMap(miniLayer, {
          toggleDisplay: true,
          minimized: true
        }).addTo(this._map);
      }
    }
  },
  getMap: function () {
    return this._map;
  },
  getLayerCtrl: function () {
    return this._layerCtrl;
  },
  newLayer: function (opts) {
    if (!opts) {
      return;
    }
    var layer;
    switch (opts.type) {
      case 'tile':
        layer = L.tileLayer(opts.url, {
          subdomains: opts.subdomains
        });
        break;
      case 'wms':
        layer = L.tileLayer.wms(opts.url, {
          layers: opts.layers,
          format: opts.format || 'image/png',
          transparent: opts.transparent || true,
          version: opts.version || '1.1.1',
          crs: opts.crs || null
        });
        break;
    }
    return layer;
  },
  addBaseLayer: function (opts) {
    var layer = opts.layer;
    if (!layer) {
      layer = this.newLayer(opts);
    }
    if (this._layerCtrl) {
      this._layerCtrl.addBaseLayer(layer, opts.name);
    } else {
      this._map.addLayer(layer);
    }
  },
  addOverlay: function (opts) {
    if (!opts) {
      return;
    }
    var layer = opts.layer;
    if (!layer) {
      layer = this.newLayer(opts);
    }
    if (this._layerCtrl) {
      this._layerCtrl.addOverlay(layer, opts.name);
    }
    if (opts.show) {
      this._map.addLayer(layer);
    }
  },
  removeLayer: function (layer) {
    if (layer) {
      this._layerCtrl.removeLayer(layer);
      this._map.removeLayer(layer);
    }
  }
});
L.nsnMap = function (id, opts) {
  return new L.NsnMap(id, opts);
};

/*
 * Leaflet Control Search v2.3.0 - 2017-08-22
 *
 * Copyright 2017 Stefano Cudini
 * stefano.cudini@gmail.com
 * http://labs.easyblog.it/
 *
 * Licensed under the MIT license.
 *
 * Demo:
 * http://labs.easyblog.it/maps/leaflet-search/
 *
 * Source:
 * git@github.com:stefanocudini/leaflet-search.git
 *
 */
/*
 Name          Data passed        Description

 Managed Events:
 search:locationfound {latlng, title, layer} fired after moved and show markerLocation
 search:expanded    {}             fired after control was expanded
 search:collapsed   {}             fired after control was collapsed

 Public methods:
 setLayer()       L.LayerGroup()         set layer search at runtime
 showAlert()            'Text message'         show alert message
 searchText()     'Text searched'        search text by external code
 */

//TODO implement can do research on multiple sources layers and remote
//TODO history: false,    //show latest searches in tooltip
//FIXME option condition problem {autoCollapse: true, markerLocation: true} not show location
//FIXME option condition problem {autoCollapse: false }
//
//TODO here insert function that search inputText FIRST in _recordsCache keys and if not find results..
//  run one of callbacks search(sourceData,jsonpUrl or options.layer) and run this.showTooltip
//
//TODO change structure of _recordsCache
//  like this: _recordsCache = {"text-key1": {loc:[lat,lng], ..other attributes.. }, {"text-key2": {loc:[lat,lng]}...}, ...}
//  in this mode every record can have a free structure of attributes, only 'loc' is required
//TODO important optimization!!! always append data in this._recordsCache
//  now _recordsCache content is emptied and replaced with new data founded
//  always appending data on _recordsCache give the possibility of caching ajax, jsonp and layersearch!
//
//TODO here insert function that search inputText FIRST in _recordsCache keys and if not find results..
//  run one of callbacks search(sourceData,jsonpUrl or options.layer) and run this.showTooltip
//
//TODO change structure of _recordsCache
//  like this: _recordsCache = {"text-key1": {loc:[lat,lng], ..other attributes.. }, {"text-key2": {loc:[lat,lng]}...}, ...}
//  in this way every record can have a free structure of attributes, only 'loc' is required

(function (factory) {
  if (typeof define === 'function' && define.amd) {
    //AMD
    define(['leaflet'], factory);
  } else if (typeof module !== 'undefined') {
    // Node/CommonJS
    module.exports = factory(require('leaflet'));
  } else {
    // Browser globals
    if (typeof window.L === 'undefined')
      throw 'Leaflet must be loaded first';
    factory(window.L);
  }
})(function (L) {

  function _getPath(obj, prop) {
    var parts = prop.split('.'),
      last = parts.pop(),
      len = parts.length,
      cur = parts[0],
      i = 1;

    if (len > 0)
      while ((obj = obj[cur]) && i < len)
        cur = parts[i++];

    if (obj)
      return obj[last];
  }

  function _isObject(obj) {
    return Object.prototype.toString.call(obj) === "[object Object]";
  }

  L.Control.Search = L.Control.extend({
    includes: L.Evented.prototype,
    options: {
      url: '',            //url for search by ajax request, ex: "search.php?q={s}". Can be function that returns string for dynamic parameter setting
      layer: null,          //layer where search markers(is a L.LayerGroup)
      sourceData: null,       //function that fill _recordsCache, passed searching text by first param and callback in second
      //TODO implements uniq option 'sourceData' that recognizes source type: url,array,callback or layer
      jsonpParam: null,       //jsonp param name for search by jsonp service, ex: "callback"
      propertyLoc: 'loc',       //field for remapping location, using array: ['latname','lonname'] for select double fields(ex. ['lat','lon'] ) support dotted format: 'prop.subprop.title'
      propertyName: 'title',      //property in marker.options(or feature.properties for vector layer) trough filter elements in layer,
      formatData: null,       //callback for reformat all data from source to indexed data object
      filterData: null,       //callback for filtering data from text searched, params: textSearch, allRecords
      moveToLocation: null,     //callback run on location found, params: latlng, title, map
      buildTip: null,         //function that return row tip html node(or html string), receive text tooltip in first param
      container: '',          //container id to insert Search Control
      zoom: null,           //default zoom level for move to location
      minLength: 1,         //minimal text length for autocomplete
      initial: true,          //search elements only by initial text
      casesensitive: false,     //search elements in case sensitive text
      autoType: true,         //complete input with first suggested result and select this filled-in text.
      delayType: 400,         //delay while typing for show tooltip
      tooltipLimit: -1,       //limit max results to show in tooltip. -1 for no limit, 0 for no results
      tipAutoSubmit: true,      //auto map panTo when click on tooltip
      firstTipSubmit: false,      //auto select first result con enter click
      autoResize: true,       //autoresize on input change
      collapsed: true,        //collapse search control at startup
      autoCollapse: false,      //collapse search control after submit(on button or on tips if enabled tipAutoSubmit)
      autoCollapseTime: 1200,     //delay for autoclosing alert and collapse after blur
      textErr: 'Location not found',  //error message
      textCancel: 'Cancel',       //title in cancel button
      textPlaceholder: 'Search...',   //placeholder value
      hideMarkerOnCollapse: false,    //remove circle and marker on search control collapsed
      position: 'topleft',
      marker: {           //custom L.Marker or false for hide
        icon: false,        //custom L.Icon for maker location or false for hide
        animate: true,        //animate a circle over location found
        circle: {         //draw a circle in location found
          radius: 10,
          weight: 3,
          color: '#e03',
          stroke: true,
          fill: false
        }
      }
    },

    initialize: function (options) {
      L.Util.setOptions(this, options || {});
      this._inputMinSize = this.options.textPlaceholder ? this.options.textPlaceholder.length : 10;
      this._layer = this.options.layer || new L.LayerGroup();
      this._filterData = this.options.filterData || this._defaultFilterData;
      this._formatData = this.options.formatData || this._defaultFormatData;
      this._moveToLocation = this.options.moveToLocation || this._defaultMoveToLocation;
      this._autoTypeTmp = this.options.autoType;  //useful for disable autoType temporarily in delete/backspace keydown
      this._countertips = 0;    //number of tips items
      this._recordsCache = {};  //key,value table! that store locations! format: key,latlng
      this._curReq = null;
    },

    onAdd: function (map) {
      this._map = map;
      this._container = L.DomUtil.create('div', 'leaflet-control-search');
      this._input = this._createInput(this.options.textPlaceholder, 'search-input');
      this._tooltip = this._createTooltip('search-tooltip');
      this._cancel = this._createCancel(this.options.textCancel, 'search-cancel');
      this._button = this._createButton(this.options.textPlaceholder, 'search-button');
      this._alert = this._createAlert('search-alert');

      if (this.options.collapsed === false)
        this.expand(this.options.collapsed);

      if (this.options.marker) {

        if (this.options.marker instanceof L.Marker || this.options.marker instanceof L.CircleMarker)
          this._markerSearch = this.options.marker;

        else if (_isObject(this.options.marker))
          this._markerSearch = new L.Control.Search.Marker([0, 0], this.options.marker);

        this._markerSearch._isMarkerSearch = true;
      }

      this.setLayer(this._layer);

      map.on({
        //    'layeradd': this._onLayerAddRemove,
        //    'layerremove': this._onLayerAddRemove
        'resize': this._handleAutoresize
      }, this);
      return this._container;
    },
    addTo: function (map) {

      if (this.options.container) {
        this._container = this.onAdd(map);
        this._wrapper = L.DomUtil.get(this.options.container);
        this._wrapper.style.position = 'relative';
        this._wrapper.appendChild(this._container);
      }
      else
        L.Control.prototype.addTo.call(this, map);

      return this;
    },

    onRemove: function (map) {
      this._recordsCache = {};
      // map.off({
      //    'layeradd': this._onLayerAddRemove,
      //    'layerremove': this._onLayerAddRemove
      //  }, this);
    },

    // _onLayerAddRemove: function(e) {
    //  //without this, run setLayer also for each Markers!! to optimize!
    //  if(e.layer instanceof L.LayerGroup)
    //    if( L.stamp(e.layer) != L.stamp(this._layer) )
    //      this.setLayer(e.layer);
    // },

    setLayer: function (layer) { //set search layer at runtime
      //this.options.layer = layer; //setting this, run only this._recordsFromLayer()
      this._layer = layer;
      this._layer.addTo(this._map);
      return this;
    },

    showAlert: function (text) {
      text = text || this.options.textErr;
      this._alert.style.display = 'block';
      this._alert.innerHTML = text;
      clearTimeout(this.timerAlert);
      var that = this;
      this.timerAlert = setTimeout(function () {
        that.hideAlert();
      }, this.options.autoCollapseTime);
      return this;
    },

    hideAlert: function () {
      this._alert.style.display = 'none';
      return this;
    },

    cancel: function () {
      this._input.value = '';
      this._handleKeypress({keyCode: 8});//simulate backspace keypress
      this._input.size = this._inputMinSize;
      this._input.focus();
      this._cancel.style.display = 'none';
      this._hideTooltip();
      return this;
    },

    expand: function (toggle) {
      toggle = typeof toggle === 'boolean' ? toggle : true;
      this._input.style.display = 'block';
      L.DomUtil.addClass(this._container, 'search-exp');
      if (toggle !== false) {
        this._input.focus();
        this._map.on('dragstart click', this.collapse, this);
      }
      this.fire('search:expanded');
      return this;
    },

    collapse: function () {
      this._hideTooltip();
      this.cancel();
      this._alert.style.display = 'none';
      this._input.blur();
      if (this.options.collapsed) {
        this._input.style.display = 'none';
        this._cancel.style.display = 'none';
        L.DomUtil.removeClass(this._container, 'search-exp');
        if (this.options.hideMarkerOnCollapse) {
          this._map.removeLayer(this._markerSearch);
        }
        this._map.off('dragstart click', this.collapse, this);
      }
      this.fire('search:collapsed');
      return this;
    },

    collapseDelayed: function () { //collapse after delay, used on_input blur
      if (!this.options.autoCollapse) return this;
      var that = this;
      clearTimeout(this.timerCollapse);
      this.timerCollapse = setTimeout(function () {
        that.collapse();
      }, this.options.autoCollapseTime);
      return this;
    },

    collapseDelayedStop: function () {
      clearTimeout(this.timerCollapse);
      return this;
    },

    ////start DOM creations
    _createAlert: function (className) {
      var alert = L.DomUtil.create('div', className, this._container);
      alert.style.display = 'none';

      L.DomEvent
        .on(alert, 'click', L.DomEvent.stop, this)
        .on(alert, 'click', this.hideAlert, this);

      return alert;
    },

    _createInput: function (text, className) {
      var label = L.DomUtil.create('label', className, this._container);
      var input = L.DomUtil.create('input', className, this._container);
      input.type = 'text';
      input.size = this._inputMinSize;
      input.value = '';
      input.autocomplete = 'off';
      input.autocorrect = 'off';
      input.autocapitalize = 'off';
      input.placeholder = text;
      input.style.display = 'none';
      input.role = 'search';
      input.id = input.role + input.type + input.size;

      label.htmlFor = input.id;
      label.style.display = 'none';
      label.value = text;

      L.DomEvent
        .disableClickPropagation(input)
        .on(input, 'keydown', this._handleKeypress, this)
        .on(input, 'blur', this.collapseDelayed, this)
        .on(input, 'focus', this.collapseDelayedStop, this);

      return input;
    },

    _createCancel: function (title, className) {
      var cancel = L.DomUtil.create('a', className, this._container);
      cancel.href = '#';
      cancel.title = title;
      cancel.style.display = 'none';
      cancel.innerHTML = "<span>&otimes;</span>";//imageless(see css)

      L.DomEvent
        .on(cancel, 'click', L.DomEvent.stop, this)
        .on(cancel, 'click', this.cancel, this);

      return cancel;
    },

    _createButton: function (title, className) {
      var button = L.DomUtil.create('a', className, this._container);
      button.href = '#';
      button.title = title;

      L.DomEvent
        .on(button, 'click', L.DomEvent.stop, this)
        .on(button, 'click', this._handleSubmit, this)
        .on(button, 'focus', this.collapseDelayedStop, this)
        .on(button, 'blur', this.collapseDelayed, this);

      return button;
    },

    _createTooltip: function (className) {
      var tool = L.DomUtil.create('ul', className, this._container);
      tool.style.display = 'none';

      var that = this;
      L.DomEvent
        .disableClickPropagation(tool)
        .on(tool, 'blur', this.collapseDelayed, this)
        .on(tool, 'mousewheel', function (e) {
          that.collapseDelayedStop();
          L.DomEvent.stopPropagation(e);//disable zoom map
        }, this)
        .on(tool, 'mouseover', function (e) {
          that.collapseDelayedStop();
        }, this);
      return tool;
    },

    _createTip: function (text, val) {//val is object in recordCache, usually is Latlng
      var tip;

      if (this.options.buildTip) {
        tip = this.options.buildTip.call(this, text, val); //custom tip node or html string
        if (typeof tip === 'string') {
          var tmpNode = L.DomUtil.create('div');
          tmpNode.innerHTML = tip;
          tip = tmpNode.firstChild;
        }
      }
      else {
        tip = L.DomUtil.create('li', '');
        tip.innerHTML = text;
      }

      L.DomUtil.addClass(tip, 'search-tip');
      tip._text = text; //value replaced in this._input and used by _autoType

      if (this.options.tipAutoSubmit)
        L.DomEvent
          .disableClickPropagation(tip)
          .on(tip, 'click', L.DomEvent.stop, this)
          .on(tip, 'click', function (e) {
            this._input.value = text;
            this._handleAutoresize();
            this._input.focus();
            this._hideTooltip();
            this._handleSubmit();
          }, this);

      return tip;
    },

    //////end DOM creations

    _getUrl: function (text) {
      return (typeof this.options.url === 'function') ? this.options.url(text) : this.options.url;
    },

    _defaultFilterData: function (text, records) {

      var I, icase, regSearch, frecords = {};

      text = text.replace(/[.*+?^${}()|[\]\\]/g, '');  //sanitize remove all special characters
      if (text === '')
        return [];

      I = this.options.initial ? '^' : '';  //search only initial text
      icase = !this.options.casesensitive ? 'i' : undefined;

      regSearch = new RegExp(I + text, icase);

      //TODO use .filter or .map
      for (var key in records) {
        if (regSearch.test(key))
          frecords[key] = records[key];
      }

      return frecords;
    },

    showTooltip: function (records) {


      this._countertips = 0;
      this._tooltip.innerHTML = '';
      this._tooltip.currentSelection = -1;  //inizialized for _handleArrowSelect()

      if (this.options.tooltipLimit) {
        for (var key in records)//fill tooltip
        {
          if (this._countertips === this.options.tooltipLimit)
            break;

          this._countertips++;

          this._tooltip.appendChild(this._createTip(key, records[key]));
        }
      }

      if (this._countertips > 0) {
        this._tooltip.style.display = 'block';

        if (this._autoTypeTmp)
          this._autoType();

        this._autoTypeTmp = this.options.autoType;//reset default value
      }
      else
        this._hideTooltip();

      this._tooltip.scrollTop = 0;

      return this._countertips;
    },

    _hideTooltip: function () {
      this._tooltip.style.display = 'none';
      this._tooltip.innerHTML = '';
      return 0;
    },

    _defaultFormatData: function (json) {  //default callback for format data to indexed data
      var propName = this.options.propertyName,
        propLoc = this.options.propertyLoc,
        i, jsonret = {};

      if (L.Util.isArray(propLoc))
        for (i in json)
          jsonret[_getPath(json[i], propName)] = L.latLng(json[i][propLoc[0]], json[i][propLoc[1]]);
      else
        for (i in json)
          jsonret[_getPath(json[i], propName)] = L.latLng(_getPath(json[i], propLoc));
      //TODO throw new Error("propertyName '"+propName+"' not found in JSON data");
      return jsonret;
    },

    _recordsFromJsonp: function (text, callAfter) {  //extract searched records from remote jsonp service
      L.Control.Search.callJsonp = callAfter;
      var script = L.DomUtil.create('script', 'leaflet-search-jsonp', document.getElementsByTagName('body')[0]),
        url = L.Util.template(this._getUrl(text) + '&' + this.options.jsonpParam + '=L.Control.Search.callJsonp', {s: text}); //parsing url
      //rnd = '&_='+Math.floor(Math.random()*10000);
      //TODO add rnd param or randomize callback name! in recordsFromJsonp
      script.type = 'text/javascript';
      script.src = url;
      return {
        abort: function () {
          script.parentNode.removeChild(script);
        }
      };
    },

    _recordsFromAjax: function (text, callAfter) { //Ajax request
      if (window.XMLHttpRequest === undefined) {
        window.XMLHttpRequest = function () {
          try {
            return new ActiveXObject("Microsoft.XMLHTTP.6.0");
          }
          catch (e1) {
            try {
              return new ActiveXObject("Microsoft.XMLHTTP.3.0");
            }
            catch (e2) {
              throw new Error("XMLHttpRequest is not supported");
            }
          }
        };
      }
      var IE8or9 = ( L.Browser.ie && !window.atob && document.querySelector ),
        request = IE8or9 ? new XDomainRequest() : new XMLHttpRequest(),
        url = L.Util.template(this._getUrl(text), {s: text});

      //rnd = '&_='+Math.floor(Math.random()*10000);
      //TODO add rnd param or randomize callback name! in recordsFromAjax

      request.open("GET", url);
      var that = this;

      request.onload = function () {
        callAfter(JSON.parse(request.responseText));
      };
      request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
          this.onload();
        }
      };

      request.send();
      return request;
    },

    _searchInLayer: function (layer, retRecords, propName) {
      var that = this,
        loc;

      if (layer instanceof L.Control.Search.Marker) return;

      if (layer instanceof L.Marker || layer instanceof L.CircleMarker) {
        if (_getPath(layer.options, propName)) {
          loc = layer.getLatLng();
          loc.layer = layer;
          retRecords[_getPath(layer.options, propName)] = loc;
        }
        else if (_getPath(layer.feature.properties, propName)) {
          loc = layer.getLatLng();
          loc.layer = layer;
          retRecords[_getPath(layer.feature.properties, propName)] = loc;
        }
        else {
          //throw new Error("propertyName '"+propName+"' not found in marker");

        }
      }
      if (layer instanceof L.Path || layer instanceof L.MultiPolyline || layer instanceof L.MultiPolygon) {
        if (_getPath(layer.options, propName)) {
          loc = layer.getBounds().getCenter();
          loc.layer = layer;
          retRecords[_getPath(layer.options, propName)] = loc;
        }
        else if (_getPath(layer.feature.properties, propName)) {
          loc = layer.getBounds().getCenter();
          loc.layer = layer;
          retRecords[_getPath(layer.feature.properties, propName)] = loc;
        }
        else {
          //throw new Error("propertyName '"+propName+"' not found in shape");

        }
      }
      else if (layer.hasOwnProperty('feature'))//GeoJSON
      {
        if (layer.feature.properties.hasOwnProperty(propName)) {
          if (layer.getLatLng && typeof layer.getLatLng === 'function') {
            loc = layer.getLatLng();
            loc.layer = layer;
            retRecords[layer.feature.properties[propName]] = loc;
          } else if (layer.getBounds && typeof layer.getBounds === 'function') {
            loc = layer.getBounds().getCenter();
            loc.layer = layer;
            retRecords[layer.feature.properties[propName]] = loc;
          } else {

          }
        }
        else {
          //throw new Error("propertyName '"+propName+"' not found in feature");

        }
      }
      else if (layer instanceof L.LayerGroup) {
        layer.eachLayer(function (layer) {
          that._searchInLayer(layer, retRecords, propName);
        });
      }
    },

    _recordsFromLayer: function () { //return table: key,value from layer
      var that = this,
        retRecords = {},
        propName = this.options.propertyName;

      this._layer.eachLayer(function (layer) {
        that._searchInLayer(layer, retRecords, propName);
      });

      return retRecords;
    },

    _autoType: function () {

      //TODO implements autype without selection(useful for mobile device)

      var start = this._input.value.length,
        firstRecord = this._tooltip.firstChild ? this._tooltip.firstChild._text : '',
        end = firstRecord.length;

      if (firstRecord.indexOf(this._input.value) === 0) { // If prefix match
        this._input.value = firstRecord;
        this._handleAutoresize();

        if (this._input.createTextRange) {
          var selRange = this._input.createTextRange();
          selRange.collapse(true);
          selRange.moveStart('character', start);
          selRange.moveEnd('character', end);
          selRange.select();
        }
        else if (this._input.setSelectionRange) {
          this._input.setSelectionRange(start, end);
        }
        else if (this._input.selectionStart) {
          this._input.selectionStart = start;
          this._input.selectionEnd = end;
        }
      }
    },

    _hideAutoType: function () { // deselect text:

      var sel;
      if ((sel = this._input.selection) && sel.empty) {
        sel.empty();
      }
      else if (this._input.createTextRange) {
        sel = this._input.createTextRange();
        sel.collapse(true);
        var end = this._input.value.length;
        sel.moveStart('character', end);
        sel.moveEnd('character', end);
        sel.select();
      }
      else {
        if (this._input.getSelection) {
          this._input.getSelection().removeAllRanges();
        }
        this._input.selectionStart = this._input.selectionEnd;
      }
    },

    _handleKeypress: function (e) { //run _input keyup event

      switch (e.keyCode) {
        case 27://Esc
          this.collapse();
          break;
        case 13://Enter
          if (this._countertips == 1 || (this.options.firstTipSubmit && this._countertips > 0))
            if (this._tooltip.currentSelection == -1)
              this._handleArrowSelect(1);
          this._handleSubmit(); //do search
          break;
        case 38://Up
          this._handleArrowSelect(-1);
          break;
        case 40://Down
          this._handleArrowSelect(1);
          break;
        case  8://Backspace
        case 45://Insert
        case 46://Delete
          this._autoTypeTmp = false;//disable temporarily autoType
          break;
        case 37://Left
        case 39://Right
        case 16://Shift
        case 17://Ctrl
        case 35://End
        case 36://Home
          break;
        default://All keys

          if (this._input.value.length)
            this._cancel.style.display = 'block';
          else
            this._cancel.style.display = 'none';

          if (this._input.value.length >= this.options.minLength) {
            var that = this;

            clearTimeout(this.timerKeypress); //cancel last search request while type in
            this.timerKeypress = setTimeout(function () {  //delay before request, for limit jsonp/ajax request

              that._fillRecordsCache();

            }, this.options.delayType);
          }
          else
            this._hideTooltip();
      }

      this._handleAutoresize();
    },

    searchText: function (text) {
      var code = text.charCodeAt(text.length);

      this._input.value = text;

      this._input.style.display = 'block';
      L.DomUtil.addClass(this._container, 'search-exp');

      this._autoTypeTmp = false;

      this._handleKeypress({keyCode: code});
    },

    _fillRecordsCache: function () {

      var inputText = this._input.value,
        that = this, records;

      if (this._curReq && this._curReq.abort)
        this._curReq.abort();
      //abort previous requests

      L.DomUtil.addClass(this._container, 'search-load');

      if (this.options.layer) {
        //TODO _recordsFromLayer must return array of objects, formatted from _formatData
        this._recordsCache = this._recordsFromLayer();

        records = this._filterData(this._input.value, this._recordsCache);

        this.showTooltip(records);

        L.DomUtil.removeClass(this._container, 'search-load');
      }
      else {
        if (this.options.sourceData)
          this._retrieveData = this.options.sourceData;

        else if (this.options.url) //jsonp or ajax
          this._retrieveData = this.options.jsonpParam ? this._recordsFromJsonp : this._recordsFromAjax;

        this._curReq = this._retrieveData.call(this, inputText, function (data) {

          that._recordsCache = that._formatData(data);

          //TODO refact!
          if (that.options.sourceData)
            records = that._filterData(that._input.value, that._recordsCache);
          else
            records = that._recordsCache;

          that.showTooltip(records);

          L.DomUtil.removeClass(that._container, 'search-load');
        });
      }
    },

    _handleAutoresize: function () { //autoresize this._input
      //TODO refact _handleAutoresize now is not accurate
      if (this._input.style.maxWidth != this._map._container.offsetWidth) //If maxWidth isn't the same as when first set, reset to current Map width
        this._input.style.maxWidth = L.DomUtil.getStyle(this._map._container, 'width');

      if (this.options.autoResize && (this._container.offsetWidth + 45 < this._map._container.offsetWidth))
        this._input.size = this._input.value.length < this._inputMinSize ? this._inputMinSize : this._input.value.length;
    },

    _handleArrowSelect: function (velocity) {

      var searchTips = this._tooltip.hasChildNodes() ? this._tooltip.childNodes : [];

      for (i = 0; i < searchTips.length; i++)
        L.DomUtil.removeClass(searchTips[i], 'search-tip-select');

      if ((velocity == 1 ) && (this._tooltip.currentSelection >= (searchTips.length - 1))) {// If at end of list.
        L.DomUtil.addClass(searchTips[this._tooltip.currentSelection], 'search-tip-select');
      }
      else if ((velocity == -1 ) && (this._tooltip.currentSelection <= 0)) { // Going back up to the search box.
        this._tooltip.currentSelection = -1;
      }
      else if (this._tooltip.style.display != 'none') {
        this._tooltip.currentSelection += velocity;

        L.DomUtil.addClass(searchTips[this._tooltip.currentSelection], 'search-tip-select');

        this._input.value = searchTips[this._tooltip.currentSelection]._text;

        // scroll:
        var tipOffsetTop = searchTips[this._tooltip.currentSelection].offsetTop;

        if (tipOffsetTop + searchTips[this._tooltip.currentSelection].clientHeight >= this._tooltip.scrollTop + this._tooltip.clientHeight) {
          this._tooltip.scrollTop = tipOffsetTop - this._tooltip.clientHeight + searchTips[this._tooltip.currentSelection].clientHeight;
        }
        else if (tipOffsetTop <= this._tooltip.scrollTop) {
          this._tooltip.scrollTop = tipOffsetTop;
        }
      }
    },

    _handleSubmit: function () { //button and tooltip click and enter submit

      this._hideAutoType();

      this.hideAlert();
      this._hideTooltip();

      if (this._input.style.display == 'none') //on first click show _input only
        this.expand();
      else {
        if (this._input.value === '')  //hide _input only
          this.collapse();
        else {
          var loc = this._getLocation(this._input.value);

          if (loc === false)
            this.showAlert();
          else {
            this.showLocation(loc, this._input.value);
            this.fire('search:locationfound', {
              latlng: loc,
              text: this._input.value,
              layer: loc.layer ? loc.layer : null
            });
          }
        }
      }
    },

    _getLocation: function (key) { //extract latlng from _recordsCache

      if (this._recordsCache.hasOwnProperty(key))
        return this._recordsCache[key];//then after use .loc attribute
      else
        return false;
    },

    _defaultMoveToLocation: function (latlng, title, map) {
      if (this.options.zoom)
        this._map.setView(latlng, this.options.zoom);
      else
        this._map.panTo(latlng);
    },

    showLocation: function (latlng, title) { //set location on map from _recordsCache
      var self = this;

      self._map.once('moveend zoomend', function (e) {

        if (self._markerSearch) {
          self._markerSearch.addTo(self._map).setLatLng(latlng);
        }

      });

      self._moveToLocation(latlng, title, self._map);
      //FIXME autoCollapse option hide self._markerSearch before that visualized!!
      if (self.options.autoCollapse)
        self.collapse();

      return self;
    }
  });

  L.Control.Search.Marker = L.Marker.extend({

    includes: L.Evented.prototype,

    options: {
      icon: new L.Icon.Default(),
      animate: true,
      circle: {
        radius: 10,
        weight: 3,
        color: '#e03',
        stroke: true,
        fill: false
      }
    },

    initialize: function (latlng, options) {
      L.setOptions(this, options);

      if (options.icon === true)
        options.icon = new L.Icon.Default();

      L.Marker.prototype.initialize.call(this, latlng, options);

      if (_isObject(this.options.circle))
        this._circleLoc = new L.CircleMarker(latlng, this.options.circle);
    },

    onAdd: function (map) {
      L.Marker.prototype.onAdd.call(this, map);
      if (this._circleLoc) {
        map.addLayer(this._circleLoc);
        if (this.options.animate)
          this.animate();
      }
    },

    onRemove: function (map) {
      L.Marker.prototype.onRemove.call(this, map);
      if (this._circleLoc)
        map.removeLayer(this._circleLoc);
    },

    setLatLng: function (latlng) {
      L.Marker.prototype.setLatLng.call(this, latlng);
      if (this._circleLoc)
        this._circleLoc.setLatLng(latlng);
      return this;
    },

    _initIcon: function () {
      if (this.options.icon)
        L.Marker.prototype._initIcon.call(this);
    },

    _removeIcon: function () {
      if (this.options.icon)
        L.Marker.prototype._removeIcon.call(this);
    },

    animate: function () {
      //TODO refact animate() more smooth! like this: http://goo.gl/DDlRs
      if (this._circleLoc) {
        var circle = this._circleLoc,
          tInt = 200, //time interval
          ss = 5, //frames
          mr = parseInt(circle._radius / ss),
          oldrad = this.options.circle.radius,
          newrad = circle._radius * 2,
          acc = 0;

        circle._timerAnimLoc = setInterval(function () {
          acc += 0.5;
          mr += acc;  //adding acceleration
          newrad -= mr;

          circle.setRadius(newrad);

          if (newrad < oldrad) {
            clearInterval(circle._timerAnimLoc);
            circle.setRadius(oldrad);//reset radius
            //if(typeof afterAnimCall == 'function')
            //afterAnimCall();
            //TODO use create event 'animateEnd' in L.Control.Search.Marker
          }
        }, tInt);
      }

      return this;
    }
  });

  L.Map.addInitHook(function () {
    if (this.options.searchControl) {
      this.searchControl = L.control.search(this.options.searchControl);
      this.addControl(this.searchControl);
    }
  });

  L.control.search = function (options) {
    return new L.Control.Search(options);
  };

  return L.Control.Search;

});
/*
 * Extends L.Map to synchronize the interaction on one map to one or more other maps.
 */

(function () {
  var NO_ANIMATION = {
    animate: false,
    reset: true,
    disableViewprereset: true
  };

  L.Sync = function () {
  };
  /*
     * Helper function to compute the offset easily.
     *
     * The arguments are relative positions with respect to reference and target maps of
     * the point to sync. If you provide ratioRef=[0, 1], ratioTarget=[1, 0] will sync the
     * bottom left corner of the reference map with the top right corner of the target map.
     * The values can be less than 0 or greater than 1. It will sync points out of the map.
     */
  L.Sync.offsetHelper = function (ratioRef, ratioTarget) {
    var or = L.Util.isArray(ratioRef) ? ratioRef : [0.5, 0.5];
    var ot = L.Util.isArray(ratioTarget) ? ratioTarget : [0.5, 0.5];
    return function (center, zoom, refMap, targetMap) {
      var rs = refMap.getSize();
      var ts = targetMap.getSize();
      var pt = refMap.project(center, zoom)
        .subtract([(0.5 - or[0]) * rs.x, (0.5 - or[1]) * rs.y])
        .add([(0.5 - ot[0]) * ts.x, (0.5 - ot[1]) * ts.y]);
      return refMap.unproject(pt, zoom);
    };
  };


  L.Map.include({
    sync: function (map, options) {
      this._initSync();
      options = L.extend({
        noInitialSync: false,
        syncCursor: false,
        syncCursorMarkerOptions: {
          radius: 10,
          fillOpacity: 0.3,
          color: '#da291c',
          fillColor: '#fff'
        },
        offsetFn: function (center, zoom, refMap, targetMap) {
          // no transformation at all
          return center;
        }
      }, options);

      // prevent double-syncing the map:
      if (this._syncMaps.indexOf(map) === -1) {
        this._syncMaps.push(map);
        this._syncOffsetFns[L.Util.stamp(map)] = options.offsetFn;
      }

      if (!options.noInitialSync) {
        map.setView(
          options.offsetFn(this.getCenter(), this.getZoom(), this, map),
          this.getZoom(), NO_ANIMATION);
      }
      if (options.syncCursor) {
        if (typeof map.cursor === 'undefined') {
          map.cursor = L.circleMarker([0, 0], options.syncCursorMarkerOptions).addTo(map);
        }

        this._cursors.push(map.cursor);

        this.on('mousemove', this._cursorSyncMove, this);
        this.on('mouseout', this._cursorSyncOut, this);
      }

      // on these events, we should reset the view on every synced map
      // dragstart is due to inertia
      this.on('resize zoomend', this._selfSetView);
      this.on('moveend', this._syncOnMoveend);
      this.on('dragend', this._syncOnDragend);
      return this;
    },


    // unsync maps from each other
    unsync: function (map) {
      var self = this;

      if (this._cursors) {
        this._cursors.forEach(function (cursor, indx, _cursors) {
          if (cursor === map.cursor) {
            _cursors.splice(indx, 1);
          }
        });
      }

      // TODO: hide cursor in stead of moving to 0, 0
      if (map.cursor) {
        map.cursor.setLatLng([0, 0]);
      }

      if (this._syncMaps) {
        this._syncMaps.forEach(function (synced, id) {
          if (map === synced) {
            delete self._syncOffsetFns[L.Util.stamp(map)];
            self._syncMaps.splice(id, 1);
          }
        });
      }

      if (!this._syncMaps || this._syncMaps.length == 0) {
        // no more synced maps, so these events are not needed.
        this.off('resize zoomend', this._selfSetView);
        this.off('moveend', this._syncOnMoveend);
        this.off('dragend', this._syncOnDragend);
      }

      return this;
    },

    // Checks if the map is synced with anything or a specifyc map
    isSynced: function (otherMap) {
      var has = (this.hasOwnProperty('_syncMaps') && Object.keys(this._syncMaps).length > 0);
      if (has && otherMap) {
        // Look for this specific map
        has = false;
        this._syncMaps.forEach(function (synced) {
          if (otherMap == synced) {
            has = true;
          }
        });
      }
      return has;
    },


    // Callbacks for events...
    _cursorSyncMove: function (e) {
      this._cursors.forEach(function (cursor) {
        cursor.setLatLng(e.latlng);
      });
    },

    _cursorSyncOut: function (e) {
      this._cursors.forEach(function (cursor) {
        // TODO: hide cursor in stead of moving to 0, 0
        cursor.setLatLng([0, 0]);
      });
    },

    _selfSetView: function (e) {
      // reset the map, and let setView synchronize the others.
      this.setView(this.getCenter(), this.getZoom(), NO_ANIMATION);
    },

    _syncOnMoveend: function (e) {
      if (this._syncDragend) {
        // This is 'the moveend' after the dragend.
        // Without inertia, it will be right after,
        // but when inertia is on, we need this to detect that.
        this._syncDragend = false; // before calling setView!
        this._selfSetView(e);
        this._syncMaps.forEach(function (toSync) {
          toSync.fire('moveend');
        });
      }
    },

    _syncOnDragend: function (e) {
      // It is ugly to have state, but we need it in case of inertia.
      this._syncDragend = true;
    },


    // overload methods on originalMap to replay interactions on _syncMaps;
    _initSync: function () {
      if (this._syncMaps) {
        return;
      }
      var originalMap = this;

      this._syncMaps = [];
      this._cursors = [];
      this._syncOffsetFns = {};

      L.extend(originalMap, {
        setView: function (center, zoom, options, sync) {
          // Use this sandwich to disable and enable viewprereset
          // around setView call
          function sandwich(obj, fn) {
            var viewpreresets = [];
            var doit = options && options.disableViewprereset && obj && obj._events;
            if (doit) {
              // The event viewpreresets does an invalidateAll,
              // that reloads all the tiles.
              // That causes an annoying flicker.
              viewpreresets = obj._events.viewprereset;
              obj._events.viewprereset = [];
            }
            var ret = fn(obj);
            if (doit) {
              // restore viewpreresets event to its previous values
              obj._events.viewprereset = viewpreresets;
            }
            return ret;
          }

          // Looks better if the other maps 'follow' the active one,
          // so call this before _syncMaps
          var ret = sandwich(this, function (obj) {
            return L.Map.prototype.setView.call(obj, center, zoom, options);
          });

          if (!sync) {
            originalMap._syncMaps.forEach(function (toSync) {
              sandwich(toSync, function (obj) {
                return toSync.setView(
                  originalMap._syncOffsetFns[L.Util.stamp(toSync)](center, zoom, originalMap, toSync),
                  zoom, options, true);
              });
            });
          }

          return ret;
        },

        panBy: function (offset, options, sync) {
          if (!sync) {
            originalMap._syncMaps.forEach(function (toSync) {
              toSync.panBy(offset, options, true);
            });
          }
          return L.Map.prototype.panBy.call(this, offset, options);
        },

        _onResize: function (event, sync) {
          if (!sync) {
            originalMap._syncMaps.forEach(function (toSync) {
              toSync._onResize(event, true);
            });
          }
          return L.Map.prototype._onResize.call(this, event);
        },

        _stop: function (sync) {
          L.Map.prototype._stop.call(this);
          if (!sync) {
            originalMap._syncMaps.forEach(function (toSync) {
              toSync._stop(true);
            });
          }
        }
      });

      originalMap.dragging._draggable._updatePosition = function () {
        L.Draggable.prototype._updatePosition.call(this);
        var self = this;
        originalMap._syncMaps.forEach(function (toSync) {
          L.DomUtil.setPosition(toSync.dragging._draggable._element, self._newPos);
          toSync.eachLayer(function (layer) {
            if (layer._google !== undefined) {
              var offsetFn = originalMap._syncOffsetFns[L.Util.stamp(toSync)];
              var center = offsetFn(originalMap.getCenter(), originalMap.getZoom(), originalMap, toSync);
              layer._google.setCenter(center);
            }
          });
          toSync.fire('move');
        });
      };
    }
  });
})();

/*
 * Leaflet.TextPath - Shows text along a polyline
 * Inspired by Tom Mac Wright article :
 * http://mapbox.com/osmdev/2012/11/20/getting-serious-about-svg/
 */

(function () {

  var __onAdd = L.Polyline.prototype.onAdd,
    __onRemove = L.Polyline.prototype.onRemove,
    __updatePath = L.Polyline.prototype._updatePath,
    __bringToFront = L.Polyline.prototype.bringToFront;


  var PolylineTextPath = {

    onAdd: function () {
      __onAdd.call(this);
      this._textRedraw();
    },

    onRemove: function () {
      if (this._textNode)
        this._renderer._rootGroup.removeChild(this._textNode);
      __onRemove.call(this);
    },

    bringToFront: function () {
      __bringToFront.call(this);
      this._textRedraw();
    },

    _updatePath: function () {
      __updatePath.call(this);
      this._textRedraw();
    },

    _textRedraw: function () {
      var text = this._text,
        options = this._textOptions;
      if (text) {
        this.setText(null).setText(text, options);
      }
    },

    setText: function (text, options) {
      this._text = text;
      this._textOptions = options;

      /* If not in SVG mode or Polyline not added to map yet return */
      /* setText will be called by onAdd, using value stored in this._text */
      if (!L.Browser.svg || typeof this._map === 'undefined') {
        return this;
      }

      var defaults = {
        repeat: false,
        fillColor: 'black',
        attributes: {},
        below: false,
      };
      options = L.Util.extend(defaults, options);

      /* If empty text, hide */
      if (!text) {
        if (this._textNode && this._textNode.parentNode) {
          this._renderer._rootGroup.removeChild(this._textNode);

          /* delete the node, so it will not be removed a 2nd time if the layer is later removed from the map */
          delete this._textNode;
        }
        return this;
      }

      text = text.replace(/ /g, '\u00A0');  // Non breakable spaces
      var id = 'pathdef-' + L.Util.stamp(this);
      var svg = this._renderer._rootGroup;
      this._path.setAttribute('id', id);

      if (options.repeat) {
        /* Compute single pattern length */
        var pattern = L.SVG.create('text');
        for (var attr in options.attributes)
          pattern.setAttribute(attr, options.attributes[attr]);
        pattern.appendChild(document.createTextNode(text));
        svg.appendChild(pattern);
        var alength = pattern.getComputedTextLength();
        svg.removeChild(pattern);

        /* Create string as long as path */
        text = new Array(Math.ceil(this._path.getTotalLength() / alength)).join(text);
      }

      /* Put it along the path using textPath */
      var textNode = L.SVG.create('text'),
        textPath = L.SVG.create('textPath');

      var dy = options.offset || this._path.getAttribute('stroke-width');

      textPath.setAttributeNS("http://www.w3.org/1999/xlink", "xlink:href", '#' + id);
      textNode.setAttribute('dy', dy);
      for (var attr in options.attributes)
        textNode.setAttribute(attr, options.attributes[attr]);
      textPath.appendChild(document.createTextNode(text));
      textNode.appendChild(textPath);
      this._textNode = textNode;

      if (options.below) {
        svg.insertBefore(textNode, svg.firstChild);
      }
      else {
        svg.appendChild(textNode);
      }

      /* Center text according to the path's bounding box */
      if (options.center) {
        var textLength = textNode.getComputedTextLength();
        var pathLength = this._path.getTotalLength();
        /* Set the position for the left side of the textNode */
        textNode.setAttribute('dx', ((pathLength / 2) - (textLength / 2)));
      }

      /* Change label rotation (if required) */
      if (options.orientation) {
        var rotateAngle = 0;
        switch (options.orientation) {
          case 'flip':
            rotateAngle = 180;
            break;
          case 'perpendicular':
            rotateAngle = 90;
            break;
          default:
            rotateAngle = options.orientation;
        }

        var rotatecenterX = (textNode.getBBox().x + textNode.getBBox().width / 2);
        var rotatecenterY = (textNode.getBBox().y + textNode.getBBox().height / 2);
        textNode.setAttribute('transform', 'rotate(' + rotateAngle + ' ' + rotatecenterX + ' ' + rotatecenterY + ')');
      }

      /* Initialize mouse events for the additional nodes */
      if (this.options.clickable) {
        if (L.Browser.svg || !L.Browser.vml) {
          textPath.setAttribute('class', 'leaflet-clickable');
        }

        L.DomEvent.on(textNode, 'click', this._onMouseClick, this);

        var events = ['dblclick', 'mousedown', 'mouseover',
          'mouseout', 'mousemove', 'contextmenu'];
        for (var i = 0; i < events.length; i++) {
          L.DomEvent.on(textNode, events[i], this._fireMouseEvent, this);
        }
      }

      return this;
    }
  };

  L.Polyline.include(PolylineTextPath);

  L.LayerGroup.include({
    setText: function (text, options) {
      for (var layer in this._layers) {
        if (typeof this._layers[layer].setText === 'function') {
          this._layers[layer].setText(text, options);
        }
      }
      return this;
    }
  });


})();

/*
 * L.VectorIcon is a lightweight SVG-based icon class (as opposed to the image-based L.Icon)
 * to use with L.Marker
 * for you who love svg graphics :-)
 */

L.VectorIcon = L.Icon.extend({
  options: {
    iconSize: [12, 12], // also can be set through CSS
    className: 'leaflet-vector-icon',
    svgHeight: 64,
    svgWidth: 64,
    viewBox: '0 0 64 64',
    type: 'path', // 'path' | 'circle' | 'rect'
    shape: {
      d: 'M23.963,20.834L17.5,9.64c-0.825-1.429-2.175-1.429-3,0L8.037,20.834c-0.825,1.429-0.15,2.598,1.5,2.598h12.926C24.113,23.432,24.788,22.263,23.963,20.834z' // default path command
    },
    style: {
      fill: '#333',
      stroke: '#000',
      strokeWidth: 1
    },
    text: ''
  },

  createIcon: function (oldIcon) {
    var div = (oldIcon && oldIcon.tagName === 'DIV') ? oldIcon : document.createElement('div'),
      svg = document.createElementNS('http://www.w3.org/2000/svg', 'svg'),
      g = document.createElementNS('http://www.w3.org/2000/svg', 'g'),
      options = this.options,
      figure;

    svg.setAttributeNS(null, 'version', '1.1')
    svg.setAttribute("xmlns", "http://www.w3.org/2000/svg");
    svg.setAttribute("xmlns:xlink", "http://www.w3.org/1999/xlink");
    svg.setAttribute('height', options.svgHeight + '');
    svg.setAttribute('width', options.svgWidth + '');

    svg.style.marginTop = (options.svgHeight / 2 - 6) * -1 + 'px';
    svg.style.marginLeft = (options.svgWidth / 2 - 6) * -1 + 'px';
    if (options.transform) {
      g.setAttribute('transform', options.transform);
    }

    if (options.type === 'path') {
      figure = document.createElementNS('http://www.w3.org/2000/svg', 'path');
      figure.setAttributeNS(null, 'd', options.shape.d);
    }
    else if (options.type === 'circle') {
      figure = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
      figure.setAttributeNS(null, 'cx', options.shape.cx);
      figure.setAttributeNS(null, 'cy', options.shape.cy);
      figure.setAttributeNS(null, 'r', options.shape.r);
    }
    else if (options.type === 'rect') {
      figure = document.createElementNS('http://www.w3.org/2000/svg', 'rect');
      figure.setAttributeNS(null, 'x', options.shape.x);
      figure.setAttributeNS(null, 'y', options.shape.y);
      figure.setAttributeNS(null, 'width', options.shape.width);
      figure.setAttributeNS(null, 'height', options.shape.height);
    }
    else if (options.type === 'text') {
      figure = document.createElementNS('http://www.w3.org/2000/svg', 'text');
      figure.setAttributeNS(null, 'x', options.shape.x);
      figure.setAttributeNS(null, 'y', options.shape.y);
      figure.setAttributeNS(null, 'font-family', options.style.fontFamily || 'Arial');
      figure.setAttributeNS(null, 'font-style', options.style.fontStyle || 'normal');
      figure.setAttributeNS(null, 'font-variant', options.style.fontVariant || 'normal');
      figure.setAttributeNS(null, 'font-weight', options.style.fontWeight || 'normal');
      figure.setAttributeNS(null, 'font-size', options.style.fontSize || '12');
      figure.setAttributeNS(null, 'text-anchor', options.style.textAnchor || 'middle');
      figure.setAttributeNS(null, 'text-decoration', options.style.textDecoration || 'none');
      figure.setAttributeNS(null, 'text-rendering', options.style.textRendering || 'auto');
      figure.innerHTML = options.text;
    }
    else {
      //console.log('Error: defined type of svg shape is invalid.');
    }

    figure.setAttributeNS(null, 'stroke', options.style.stroke || 'none');
    figure.setAttributeNS(null, 'stroke-width', options.style.strokeWidth);
    figure.setAttributeNS(null, 'fill', options.style.fill || 'none');

    g.appendChild(figure);
    svg.appendChild(g);
    div.appendChild(svg);

    if (options.bgPos) {
      var bgPos = L.point(options.bgPos);
      div.style.backgroundPosition = (-bgPos.x) + 'px ' + (-bgPos.y) + 'px';
    }
    this._setIconStyles(div, 'icon');

    return div;
  },

  createShadow: function () {
    return null;
  }
});

L.vectorIcon = function (options) {
  return new L.VectorIcon(options);
};

L.TileLayer.ChinaProvider = L.TileLayer.extend({

  initialize: function(type, options) { // (type, Object)
    var providers = L.TileLayer.ChinaProvider.providers;

    var parts = type.split('.');

    var providerName = parts[0];
    var mapName = parts[1];
    var mapType = parts[2];

    var url = providers[providerName][mapName][mapType];
    options.subdomains = providers[providerName].Subdomains;
    options.key = options.key || providers[providerName].key;
    L.TileLayer.prototype.initialize.call(this, url, options);
  }
});

L.TileLayer.ChinaProvider = L.TileLayer.extend({

  initialize: function(type, options) { // (type, Object)
    var providers = L.TileLayer.ChinaProvider.providers;

    var parts = type.split('.');

    var providerName = parts[0];
    var mapName = parts[1];
    var mapType = parts[2];

    var url = providers[providerName][mapName][mapType];
    options.subdomains = providers[providerName].Subdomains;
    options.key = options.key || providers[providerName].key;
    L.TileLayer.prototype.initialize.call(this, url, options);
  }
});

L.TileLayer.ChinaProvider.providers = {
  TianDiTu: {
    Normal: {
      Map: "http://t{s}.tianditu.com/DataServer?T=vec_w&X={x}&Y={y}&L={z}&tk={key}",
      Annotion: "http://t{s}.tianditu.com/DataServer?T=cva_w&X={x}&Y={y}&L={z}&tk={key}"
    },
    Satellite: {
      Map: "http://t{s}.tianditu.com/DataServer?T=img_w&X={x}&Y={y}&L={z}&tk={key}",
      Annotion: "http://t{s}.tianditu.com/DataServer?T=cia_w&X={x}&Y={y}&L={z}&tk={key}"
    },
    Terrain: {
      Map: "http://t{s}.tianditu.com/DataServer?T=ter_w&X={x}&Y={y}&L={z}&tk={key}",
      Annotion: "http://t{s}.tianditu.com/DataServer?T=cta_w&X={x}&Y={y}&L={z}&tk={key}"
    },
    Subdomains: ['0', '1', '2', '3', '4', '5', '6', '7'],
    key: "174705aebfe31b79b3587279e211cb9a"
  },

  GaoDe: {
    Normal: {
      Map: 'http://webrd0{s}.is.autonavi.com/appmaptile?lang=zh_cn&size=1&scale=1&style=8&x={x}&y={y}&z={z}'
    },
    Satellite: {
      Map: 'http://webst0{s}.is.autonavi.com/appmaptile?style=6&x={x}&y={y}&z={z}',
      Annotion: 'http://webst0{s}.is.autonavi.com/appmaptile?style=8&x={x}&y={y}&z={z}'
    },
    Subdomains: ["1", "2", "3", "4"]
  },

  Google: {
    Normal: {
      Map: "http://www.google.cn/maps/vt?lyrs=m@189&gl=cn&x={x}&y={y}&z={z}"
    },
    Satellite: {
      Map: "http://www.google.cn/maps/vt?lyrs=s@189&gl=cn&x={x}&y={y}&z={z}"
    },
    Subdomains: []
  },

  Geoq: {
    Normal: {
      Map: "http://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineCommunity/MapServer/tile/{z}/{y}/{x}",
      PurplishBlue: "http://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineStreetPurplishBlue/MapServer/tile/{z}/{y}/{x}",
      Gray: "http://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineStreetGray/MapServer/tile/{z}/{y}/{x}",
      Warm: "http://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineStreetWarm/MapServer/tile/{z}/{y}/{x}",
    },
    Theme: {
      Hydro: "http://thematic.geoq.cn/arcgis/rest/services/ThematicMaps/WorldHydroMap/MapServer/tile/{z}/{y}/{x}"
    },
    Subdomains: []
  },

  OSM: {
    Normal: {
      Map: "http://{s}.tile.osm.org/{z}/{x}/{y}.png",
    },
    Subdomains: ['a', 'b', 'c']
  }

};

L.tileLayer.chinaProvider = function(type, options) {
  return new L.TileLayer.ChinaProvider(type, options);
};
