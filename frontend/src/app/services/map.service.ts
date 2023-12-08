import { Injectable } from '@angular/core';
// import * as L from 'leaflet';
import { LatLngBounds } from 'leaflet';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import * as $ from 'jquery';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { faShoppingBasket } from '@fortawesome/free-solid-svg-icons';

declare let L;

@Injectable({
    providedIn: 'root'
})
export class MapService {
    map: L.Map;
    EEPSG: any = [
        {
            label: 'ED50 / UTM zone 35N',
            value: 'EPSG:23035',
            ProjectedBounds: [225358.6605, 3849480.5700, 774641.3395, 8888922.0027],
            proj4: '+proj=utm +zone=35 +ellps=intl +units=m +no_defs '
        },
        {
            label: 'ED50 / UTM zone 36N',
            value: 'EPSG:23036',
            ProjectedBounds: [213527.5672, 3433517.5368, 786472.4328, 4798588.2736],
            proj4: '+proj=utm +zone=36 +ellps=intl +units=m +no_defs '
        },
        {
            label: 'ED50 / UTM zone 37N',
            value: 'EPSG:23037',
            ProjectedBounds: [228986.7536, 3969312.2387, 771013.2464, 4798588.2736],
            proj4: '+proj=utm +zone=37 +ellps=intl +units=m +no_defs '
        },
        {
            label: 'ED50 / UTM zone 38N',
            value: 'EPSG:23038',
            ProjectedBounds: [233026.0015, 4099147.6709, 766973.9985, 4615347.4315],
            proj4: '+proj=utm +zone=38 +ellps=intl +units=m +no_defs '
        },
        {
            label: 'WGS84 / UTM zone 35N',
            value: 'EPSG:32635',
            ProjectedBounds: [166021.4431, 0.0000, 833978.5569, 9329005.1825],
            proj4: '+proj=utm +zone=35 +ellps=WGS84 +datum=WGS84 +units=m +no_defs '
        },
        {
            label: 'WGS84 / UTM zone 36N',
            value: 'EPSG:32636',
            ProjectedBounds: [166021.4431, 0.0000, 833978.5569, 9329005.1825],
            proj4: '+proj=utm +zone=36 +ellps=WGS84 +datum=WGS84 +units=m +no_defs '
        },
        {
            label: 'WGS84 / UTM zone 37N',
            value: 'EPSG:32637',
            ProjectedBounds: [166021.4431, 0.0000, 833978.5569, 9329005.1825],
            proj4: '+proj=utm +zone=37 +ellps=WGS84 +datum=WGS84 +units=m +no_defs '
        },
        {
            label: 'WGS84 / UTM zone 38N',
            value: 'EPSG:32638',
            ProjectedBounds: [166021.4431, 0.0000, 833978.5569, 9329005.1825],
            proj4: '+proj=utm +zone=38 +ellps=WGS84 +datum=WGS84 +units=m +no_defs '
        }
    ];
    baseUrl: string;

    searchfunctionCallBack: any;
    sideBarMenuItems: any;
    sideBarHeaderTitle: any;

    constructor(private http: HttpClient) {
        this.baseUrl = environment.apiUrl + 'map/';
    }

    Proj4Defs(proj4) {
        proj4.defs('EPSG:4326', '+title=WGS 84 (long/lat) +proj=longlat +ellps=WGS84 +datum=WGS84 +units=degrees');
        proj4.defs('CRS:84', '+title=WGS 84 (long/lat) +proj=longlat +ellps=WGS84 +datum=WGS84 +units=degrees');
        for (const item of this.EEPSG) {
            proj4.defs(item.value, item.proj4);
        }
    }

    getDefaultControlLayer(): any {
        const openStreetMap = this.getDefaultLayers().openStreetMap;

        const googleStreets = this.getDefaultLayers().googleStreets;
        const googleHybrid = this.getDefaultLayers().googleHybrid;
        const googleSat = this.getDefaultLayers().googleSat;
        const googleTerrain = this.getDefaultLayers().googleTerrain;

        const baseMaps = {
            'Google Yol Haritası': googleStreets,
            'Google Görüntüsü': googleHybrid,
            'Google Uydu Görüntüsü': googleSat,
            'Google Terrain Görüntüsü': googleTerrain,
            'Open Street Map': openStreetMap,
        };

        const overlayMaps = {
            // İller: province,
            // 'Esri Yerleşim Yeri': esriBoundries,
            // İller: vectorProvinces,
        };
        return new L.Control.Layers(baseMaps, overlayMaps);
    }

    getDefaultLayers(): any {
        const layers: any = {};
        layers.openStreetMap = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 18,
            minZoom: 3
        });

        layers.googleRoadmap = L.TileLayer.extend({
            createTile: (coords, done) => {
                const img = document.createElement('img');
                const url = this.baseUrl + 'tms/googleRoadmap/' + coords.z + '/' + coords.x + '/' + coords.y + '.png';
                this.http.get(url, { responseType: 'blob', observe: 'response' }).toPromise().then(result => {
                    img.src = URL.createObjectURL(result.body);
                    done(null, img);
                });
                return img;
            }
        });

        layers.googleHybrid = L.TileLayer.extend({
            createTile: (coords, done) => {
                const img = document.createElement('img');
                const url = this.baseUrl + 'tms/googleHybrid/' + coords.z + '/' + coords.x + '/' + coords.y + '.png';
                this.http.get(url, { responseType: 'blob', observe: 'response' }).toPromise().then(result => {
                    img.src = URL.createObjectURL(result.body);
                    done(null, img);
                });
                return img;
            }
        });

        layers.googleStreets = L.tileLayer(
            'https://{s}.google.com/vt/lyrs=m&x={x}&y={y}&z={z}&language=tr&region=TR', {
            subdomains: ['mt0', 'mt1', 'mt2', 'mt3']
        }
        )
        layers.googleHybrid = L.tileLayer(
            'https://{s}.google.com/vt/lyrs=y,h&x={x}&y={y}&z={z}', {
            subdomains: ['mt0', 'mt1', 'mt2', 'mt3']
        }
        )
        layers.googleSat = L.tileLayer(
            'https://{s}.google.com/vt/lyrs=s&x={x}&y={y}&z={z}', {
            subdomains: ['mt0', 'mt1', 'mt2', 'mt3']
        }
        )
        layers.googleTerrain = L.tileLayer(
            'https://{s}.google.com/vt/lyrs=p&x={x}&y={y}&z={z}', {
            subdomains: ['mt0', 'mt1', 'mt2', 'mt3']
        }
        )

        layers.province = new (L.TileLayer.extend({
            createTile: (coords, done) => {
                const img = document.createElement('img');
                const url = this.baseUrl + 'tms/spatial_schema.st_province/' + coords.z + '/' + coords.x + '/' + coords.y + '.png';
                this.http.get(url, { responseType: 'blob', observe: 'response' }).toPromise().then(result => {
                    img.src = URL.createObjectURL(result.body);
                    done(null, img);
                });
                return img;
            }
        }));

        layers.esriBoundries = L.tileLayer('https://services.arcgisonline.com/ArcGIS/rest/services/Reference/World_Boundaries_and_Places/MapServer/tile/{z}/{y}/{x}', {
            opacity: 1,
            zIndex: 1000
        });


        const defaultStyle = {
            color: '#FFF59D',
            weight: .5,
            opacity: 0.0,
            fillOpacity: 0
        };
        const hoverStyle = {
            color: '#de3a25',
            weight: .5,
            opacity: 0.6,
            fillOpacity: 0.6
        };
        layers.vectorProvinces = L.geoJson.ajax('/assets/geometry/tr-cities.json', {
            onEachFeature: (feature, layer) => {
                layer.setStyle(defaultStyle);
                layer.on('mouseover', (e) => {
                    layer.setStyle(hoverStyle);
                    layer.bindTooltip(feature.properties.name);
                    layer.openTooltip();
                })
                layer.on('mouseout', () => {
                    layer.setStyle(defaultStyle);
                    layer.closePopup();
                })
                layer.on('click', () => {
                    if (this.map) {
                        this.map.fire('onClickProvince', feature);
                    }
                })
            }
        });

        return layers;
    }

    getTurkeyBounds(): LatLngBounds {
        const northEast = L.latLng(42.1054115295413, 44.83383941650408);
        const southWest = L.latLng(35.815425872802905, 25.665136337280273);
        return L.latLngBounds(southWest, northEast);
    }

    getHeaders(): HttpHeaders {
        const headers = new HttpHeaders();
        const token = { access_token: 'ABCDEF' };
        if (token) {
            headers.set('Authorization', 'Bearer ' + token.access_token);
        }
        return headers;
    }

    getGeoJson(data: GeoJsonModelView): Observable<any> {
        return this.http.post(this.baseUrl + 'geoJson', data);
    }

    getPublicGeoJson(data: GeoJsonModelView) {
        return this.http.post(this.baseUrl + 'publicGeoJson', data);
    }

    addZoomInButton(map) {
        const btnIn = L.easyButton({
            position: 'topleft',
            leafletClasses: false,
            states: [{
                onClick: () => {
                    map.setZoom(map.getZoom() + 1);
                },
                icon: '<i class="material-icons" style="font-size:18px; padding-top: 5px;">add</i>',
                title: 'Yakınlaştır'
            }]
        }).addTo(map);
        $(btnIn.button).addClass('mdc-fab inline-demo-fab mdc-ripple-upgraded mdc-fab--mini');
        btnIn.button.style.backgroundColor = '#F5F7F9';
        btnIn.button.style.color = 'black';
    }

    resizeMarker(map, lat, lng) {
        const carIcon = L.icon({
            iconUrl: '../../../../../../assets/images/map/car.png',
            iconSize: [50, 50]
        })
        return L.marker([lat, lng], { icon: carIcon }).addTo(map);
    }

    addZoomOutButton(map) {
        const btnOut = L.easyButton({
            position: 'topleft',
            leafletClasses: false,
            states: [{
                onClick: () => {
                    map.setZoom(map.getZoom() - 1);
                },
                icon: '<i class="material-icons" style="font-size:18px; padding-top: 5px;">remove</i>',
                title: 'Uzaklaştır'
            }]
        }).addTo(map);
        $(btnOut.button).addClass('mdc-fab inline-demo-fab mdc-ripple-upgraded mdc-fab--mini');
        btnOut.button.style.backgroundColor = '#F5F7F9';
        btnOut.button.style.color = 'black';
    }

    addHomeButton(map, object: any) {
        return new Observable((observer) => {
            const btnHome = L.easyButton({
                position: 'topleft',
                leafletClasses: false,
                states: [{
                    stateName: 'home-button',
                    onClick: () => {
                        observer.next(object);
                    },
                    icon: '<i class="material-icons" style="font-size:18px; padding-top: 5px;">home</i>',
                    title: 'Tümü'
                }]
            }).addTo(map);
            $(btnHome.button).addClass('mdc-fab inline-demo-fab mdc-ripple-upgraded mdc-fab--mini');
            btnHome.button.style.marginTop = '20px';
            btnHome.button.style.backgroundColor = '#65AAF0';
        });
    }

    addCustomButton(map, properties: ButtonProperties, object: any) {
        const btnHome = L.easyButton({
            position: properties.position,
            leafletClasses: false,
            states: [{
                stateName: 'custom-button',
                onClick: () => {
                    object();
                },
                icon: '<i class="material-icons" style="font-size:' + properties.fontSize + '; padding-top:' + properties.paddingTop + ';">' + properties.icon + '</i>',
                title: properties.title
            }]
        }).addTo(map);
        $(btnHome.button).addClass('mdc-fab inline-demo-fab mdc-ripple-upgraded mdc-fab--mini');
        btnHome.button.style.marginTop = properties.marginTop;
        btnHome.button.style.backgroundColor = properties.backgroundColor;
    }

    addExitButtom(map, object: any) {
        return new Observable((observer) => {
            const btnHome = L.easyButton({
                position: 'bottomleft',
                leafletClasses: false,
                states: [{
                    stateName: 'exit-button',
                    onClick: () => {
                        observer.next(object);
                    },
                    icon: '<i class="material-icons" style="font-size:18px; padding-top: 5px;">exit_to_app</i>',
                    title: 'Çıkış'
                }]
            }).addTo(map);
            $(btnHome.button).addClass('mdc-fab inline-demo-fab mdc-ripple-upgraded mdc-fab--mini');
            // btnHome.button.style.marginBottom = '20px';
            btnHome.button.style.backgroundColor = '#f06581';
        });
    }

    addZoomControlButton(map) {
        L.control.fullscreen({
            position: 'topleft', // change the position of the button can be topleft, topright, bottomright or bottomleft, defaut topleft
            title: {
                false: 'Tam Ekran',
                true: 'Tam Ekran Çık'
            },
            content: null, // change the content of the button, can be HTML, default null
            forceSeparateButton: true, // force seperate button to detach from zoom buttons, default false
            forcePseudoFullscreen: true, // force use of pseudo full screen even if full screen API is available, default false
            fullscreenElement: false, // Dom element to render in full screen, false by default, fallback to map._container
        }).addTo(map);
        const btnLayers = L.easyButton({
            position: 'topleft',
            leafletClasses: false,
            states: [{
                stateName: 'enabled-fullscreen',
                onClick: (control) => {
                    map.toggleFullscreen();
                    control.state('disabled-fullscreen');
                },
                icon: '<i class="material-icons" style="font-size:18px; padding-top: 5px;">open_in_new</i>',
                title: 'Tam Ekran'
            },
            {
                stateName: 'disabled-fullscreen',
                onClick: (control) => {
                    map.toggleFullscreen();
                    control.state('enabled-fullscreen');
                },
                icon: '<i class="material-icons" style="font-size:18px; padding-top: 5px;">all_out</i>',
                title: 'Tam Ekran Çık'
            }]
        }).addTo(map);
        $(btnLayers.button).addClass('mdc-fab inline-demo-fab mdc-ripple-upgraded mdc-fab--mini');
        btnLayers.button.style.marginTop = '5px';
        btnLayers.button.style.backgroundColor = '#8f4af0';
        $('.leaflet-control-fullscreen').hide();
        map.on('fullscreenchange', (item: any) => {
            map.invalidateSize();
        });
    }

    addFullScreenControlButton(map, object: any) {
        return new Observable((observer) => {
            const btnLayers = L.easyButton({
                position: 'topright',
                leafletClasses: false,
                states: [{
                    stateName: 'enabled-layers',
                    onClick: (control) => {
                        control.state('disabled-layers');
                        const data = {
                            state: true,
                            this: object
                        };
                        observer.next(data);
                    },
                    icon: '<i class="material-icons" style="font-size:18px; padding-top: 5px;">layers</i>',
                    title: 'Göster'
                },
                {
                    stateName: 'disabled-layers',
                    onClick: (control) => {
                        control.state('enabled-layers');
                        const data = {
                            state: false,
                            this: object
                        };
                        observer.next(data);
                    },
                    icon: '<i class="material-icons" style="font-size:18px; padding-top: 5px;">check_box_outline_blank</i>',
                    title: 'Çık'
                }]
            }).addTo(map);
            $(btnLayers.button).addClass('mdc-fab inline-demo-fab mdc-ripple-upgraded mdc-fab--mini');
            btnLayers.button.style.marginTop = '5px';
            btnLayers.button.style.backgroundColor = '#12e8e8';
        });
    }

    setDraw(map, drawnItems: any) {
        map.addControl(new L.Control.Draw(
            {
                position: 'topright',
                edit: {
                    featureGroup: drawnItems,
                    // edit: false,
                    remove: true
                },
                draw: false,
                rectangle: <any>{ showArea: false },
            }
        ));
    }

    addPolygon(map) {
        const polygonDrawer = new L.Draw.Polygon(map);
        const btnIn = L.easyButton({
            position: 'topright',
            leafletClasses: false,
            states: [{
                onClick: () => {
                    polygonDrawer.enable();
                },
                icon: '<i class="material-icons" style="font-size:18px; padding-top: 5px;">terrain</i>',
                title: 'Poligon Çiz'
            }]
        }).addTo(map);
        $(btnIn.button).addClass('mdc-fab inline-demo-fab mdc-ripple-upgraded mdc-fab--mini');
        btnIn.button.style.backgroundColor = '#F5F7F9';
        btnIn.button.style.color = 'black';
        btnIn.button.style.marginTop = '20px';

    }

    addRectangle(map) {
        const options: any = { showArea: false, metric: false, type: 'rectangle' };
        const drawer = new L.Draw.Rectangle(map, options);
        const btnIn = L.easyButton({
            position: 'topright',
            leafletClasses: false,
            states: [{
                onClick: () => {
                    drawer.enable();
                },
                icon: '<i class="material-icons" style="font-size:18px; padding-top: 5px;">crop_3_2</i>',
                title: 'Dörtgen Çiz'
            }]
        }).addTo(map);
        $(btnIn.button).addClass('mdc-fab inline-demo-fab mdc-ripple-upgraded mdc-fab--mini');
        btnIn.button.style.backgroundColor = '#F5F7F9';
        btnIn.button.style.color = 'black';
    }

    addCircle(map) {
        const drawer = new L.Draw.Circle(map);
        const btnIn = L.easyButton({
            position: 'topright',
            leafletClasses: false,
            states: [{
                onClick: () => {
                    drawer.enable();
                },
                icon: '<i class="material-icons" style="font-size:18px; padding-top: 5px;">panorama_fish_eye</i>',
                title: 'Daire Çiz'
            }]
        }).addTo(map);
        $(btnIn.button).addClass('mdc-fab inline-demo-fab mdc-ripple-upgraded mdc-fab--mini');
        btnIn.button.style.backgroundColor = '#F5F7F9';
        btnIn.button.style.color = 'black';
    }

    addMarker(map) {
        const drawer = new L.Draw.Marker(map);
        const btnIn = L.easyButton({
            position: 'topright',
            leafletClasses: false,
            states: [{
                onClick: () => {
                    drawer.enable();
                },
                icon: '<i class="material-icons" style="font-size:18px; padding-top: 5px;">place</i>',
                title: 'Nokta Çiz'
            }]
        }).addTo(map);
        $(btnIn.button).addClass('mdc-fab inline-demo-fab mdc-ripple-upgraded mdc-fab--mini');
        btnIn.button.style.backgroundColor = '#F5F7F9';
        btnIn.button.style.color = 'black';
        btnIn.button.style.marginTop = '20px';
        btnIn.button.style.marginBottom = '20px';
    }

    setMap(map) {
        this.map = map;
    }

    getRandomLatLng(map) {
        const bounds = map.getBounds();
        const southWest = bounds.getSouthWest();
        const northEast = bounds.getNorthEast();
        const lngSpan = northEast.lng - southWest.lng;
        const latSpan = northEast.lat - southWest.lat;

        return new L.LatLng(
            southWest.lat + latSpan * Math.random(),
            southWest.lng + lngSpan * Math.random());
    }

    isMarkerInsidePolygon(latLng, poly) {
        let inside = false;
        const x = latLng.lat;
        const y = latLng.lng;
        poly.getLatLngs().forEach(item => {
            for (let i = 0, j = item.length - 1; i < item.length; j = i++) {
                const xi = item[i].lat;
                const yi = item[i].lng;
                const xj = item[j].lat;
                const yj = item[j].lng;

                const intersect = ((yi > y) !== (yj > y))
                    && (x < (xj - xi) * (y - yi) / (yj - yi) + xi);
                if (intersect) {
                    inside = !inside;
                }
            }
        })
        return inside;
    };

    // SearchBox
    getControlHrmlContent = () => {
        return '<div id=\"controlbox\" ><div id=\"boxcontainer\" class=\"searchbox searchbox-shadow\" > <div class=\"searchbox-menu-container\"><button aria-label=\"Menu\" id=\"searchbox-menubutton\" class=\"searchbox-menubutton\"><\/button> <span aria-hidden=\"true\" style=\"display:none\">Menu<\/span> <\/div><div><input id=\"searchboxinput\" type=\"text\" style=\"position: relative;\"\/><\/div><div class=\"searchbox-searchbutton-container\"><button aria-label=\"search\" id=\"searchbox-searchbutton\" class=\"searchbox-searchbutton\"><\/button> <span aria-hidden=\"true\" style=\"display:none;\">search<\/span> <\/div><\/div><\/div><div class=\"panel\"> <div class=\"panel-header\"> <div class=\"panel-header-container\"> <span class=\"panel-header-title\"><\/span> <button aria-label=\"Menu\" id=\"panelbutton\" class=\"panel-close-button\"><\/button> <\/div><\/div><div class=\"panel-content\"> <\/div><\/div>';
    }

    generateHtmlContent(menuItems) {
        let content = '<ul class="panel-list">';

        // tslint:disable-next-line:prefer-for-of
        for (let i = 0; i < menuItems.Items.length; i++) {
            const item = menuItems.Items[i];
            content += '<li class="panel-list-item"><div>';
            if (item.type === 'link') {
                content += '<span class=\"panel-list-item-icon ' + item.icon + '\" ></span>';
                content += '<a href=\"' + item.href + '\">' + item.name + '</a>';
            } else if (item.type === 'button') {
                content += '<span class=\"panel-list-item-icon ' + item.icon + '\" ></span>';
                content += '<button onclick=\"' + item.onclick + '\">' + item.name + '</button>';

            }
            content += '</li></div>'
        }


        content += '</ul>'
        return content;
    }

    createSearchboxControl() {
        return L.Control.extend({
            _sideBarHeaderTitle: 'Sample Title',
            options: {
                position: 'topleft'
            },
            initialize: (options) => {
                L.Util.setOptions(this, options);

                if (options.searchCallBack) {
                    this.searchfunctionCallBack = options.searchCallBack;
                }
                if (options.sidebarTitleText) {
                    this.sideBarHeaderTitle = options.sidebarTitleText;
                }
                if (options.sidebarMenuItems) {
                    this.sideBarMenuItems = options.sidebarMenuItems;
                }
            },
            onAdd: () => {
                const container = L.DomUtil.create('div');
                container.id = 'controlcontainer';
                const headerTitle = this.sideBarHeaderTitle;
                const menuItems = this.sideBarMenuItems;
                const searchCallBack = this.searchfunctionCallBack;

                const data = this.getControlHrmlContent();
                $(container).html(data);
                setTimeout(() => {
                    $('#searchbox-searchbutton').click(() => {
                        const searchkeywords = $('#searchboxinput').val();
                        searchCallBack(searchkeywords);
                    });
                    $('.panel-header-title').text(headerTitle);
                    const htmlContent = this.generateHtmlContent(menuItems);
                    $('.panel-content').html(htmlContent);
                }, 1);
                L.DomEvent.disableClickPropagation(container);
                return container;
            }

        });
    }


    toWKT(layer): any {
        // tslint:disable-next-line:one-variable-per-declaration
        let lng, lat;
        const coords: any = [];
        if (layer instanceof L.Polygon || layer instanceof L.Polyline) {
            const latlngs = layer.getLatLngs();
            for (let i = 0; i < latlngs.length; i++) {
                const latlngs1 = latlngs[i];
                if (latlngs1.length) {
                    for (let j = 0; j < latlngs1.length; j++) {
                        coords.push(latlngs1[j].lng + ' ' + latlngs1[j].lat);
                        if (j === 0) {
                            lng = latlngs1[j].lng;
                            lat = latlngs1[j].lat;
                        }
                    }
                } else {
                    coords.push(latlngs[i].lng + ' ' + latlngs[i].lat);
                    if (i === 0) {
                        lng = latlngs[i].lng;
                        lat = latlngs[i].lat;
                    }
                }
            }
            if (layer instanceof L.Polygon) {
                return 'POLYGON((' + coords.join(',') + ',' + lng + ' ' + lat + '))';
            } else if (layer instanceof L.Polyline) {
                return 'LINESTRING(' + coords.join(',') + ')';
            }
        } else if (layer instanceof L.Marker) {
            return 'POINT(' + layer.getLatLng().lng + ' ' + layer.getLatLng().lat + ')';
        }
    };

    
  protected readonly faShoppingBasket = faShoppingBasket;
}

export class GeoJsonModelView {
    layerName: string;
    'the_geom': string;
    properties: string[];
    filter: string;
    maxFeatures: number;
}

export class ButtonProperties {
    icon: string;
    backgroundColor: string;
    title: string;
    position: string;
    marginTop: string;
    fontSize: string;
    paddingTop: string;
}
