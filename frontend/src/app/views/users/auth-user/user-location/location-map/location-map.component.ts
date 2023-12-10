import { AfterViewInit, Component, ElementRef, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild } from '@angular/core';
import turfCircle from "@turf/circle";
import { MapService } from 'src/app/services/map.service';
import "leaflet-control-geocoder/dist/Control.Geocoder.js";

declare let L;

@Component({
  selector: 'app-location-map',
  templateUrl: './location-map.component.html',
  styleUrls: ['./location-map.component.scss']
})
export class LocationMapComponent implements OnChanges, AfterViewInit {
  @Input() mapData: any;
  @Output() onHidingMap: EventEmitter<any> = new EventEmitter<any>();
  drawnItems: any;
  info: any;
  geojson: any;
  @ViewChild('mapDiv', { static: true }) mapDiv: ElementRef;
  controlLayers: any;
  map: L.Map;
  turfOptions: {} = { steps: 64, units: 'meters' };

  constructor(private mapService: MapService) {
    this.setMap = this.setMap.bind(this);
  }


  ngAfterViewInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.setMap();
  }

  setMap() {
    this.map = L.map(this.mapDiv.nativeElement, {
      maxBounds: this.mapService.getTurkeyBounds(),
      minZoom: 5,
      maxZoom: 18,
      zoomControl: false,
      attributionControl: false,
    });

    this.controlLayers = this.mapService.getDefaultControlLayer();
    this.controlLayers._layers.forEach((item: any) => {
      if (item.name === 'Google Görüntüsü') {
        item.layer.addTo(this.map);
      }
    });
    this.controlLayers.addTo(this.map);
    (L.Control as any).geocoder().addTo(this.map);
    this.map.invalidateSize();

    this.drawnItems = L.featureGroup().addTo(this.map);
    this.mapService.addPolygon(this.map);
    this.mapService.addRectangle(this.map);
    this.mapService.addCircle(this.map);
    this.mapService.setDraw(this.map, this.drawnItems);

    this.map.on(L.Draw.Event.CREATED, (event: any) => {
      const type = event.layerType;
      let layer = event.layer;
      let latLongTemp = null;
      if (type === 'circle') {
        const radius = event.layer.getRadius();
        const latLong = event.layer.getLatLng();
        const center = [latLong.lng, latLong.lat];
        layer = new L.GeoJSON(turfCircle(center, radius, this.turfOptions));
        latLongTemp = latLong;
      } else if (type === 'rectangle') {
      }

      this.drawnItems.addLayer(layer);
      const lyr = this.drawnItems.toGeoJSON();
      this.onHidingMap.emit(lyr);
    });

    this.map.on(L.Draw.Event.EDITED, (event: any) => {
      const lyr = this.drawnItems.toGeoJSON();
      this.onHidingMap.emit(lyr);
    });

    this.map.on(L.Draw.Event.DELETED, (event: any) => {
      this.onHidingMap.emit(null);
    });


    this.mapService.addZoomInButton(this.map);
    this.mapService.addZoomOutButton(this.map);
    this.mapService.addHomeButton(this.map, this).subscribe({
      next(object: any): void {
        object.map.fitBounds(object.drawnItems.getBounds());
        object.map.invalidateSize();
      }
    });
    this.mapService.addZoomControlButton(this.map);
    this.map.fitBounds(this.mapService.getTurkeyBounds());
    this.map.invalidateSize();

    if (this.mapData.geom) {
      this.addGeoJson(this.mapData.geom);
    }

  }

  addGeoJson(geoJson) {
    const geoJsonLayer = L.geoJSON(geoJson);
    this.drawnItems.clearLayers();
    geoJsonLayer.eachLayer(
      (l) => {
        l.getLayers().forEach(layer => {
          this.drawnItems.addLayer(layer);
        })
      });
    this.map.fitBounds(geoJsonLayer.getBounds());
    this.map.invalidateSize();
  }
}
