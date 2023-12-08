import { AfterViewInit, Component, ElementRef, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild } from '@angular/core';
import { MapService } from "../../../../services/map.service";

declare let L;

@Component({
  selector: 'app-restaurant-map',
  templateUrl: './restaurant-map.component.html',
  styleUrls: ['./restaurant-map.component.scss']
})
export class RestaurantMapComponent implements OnChanges, AfterViewInit {
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
    this.map.invalidateSize();

    this.drawnItems = L.featureGroup().addTo(this.map);
    this.mapService.setDraw(this.map, this.drawnItems);


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

  }

}
