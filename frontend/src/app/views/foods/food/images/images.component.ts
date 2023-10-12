import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild} from '@angular/core';
import {DxDataGridComponent} from "devextreme-angular";

@Component({
  selector: 'app-images',
  templateUrl: './images.component.html',
  styleUrls: ['./images.component.scss']
})
export class ImagesComponent implements OnChanges {
  @Input() foodData: any;
  @ViewChild('dataGrid', {static: true}) dataGrid: DxDataGridComponent | undefined;
  @Output() onHidingPopup: EventEmitter<any> = new EventEmitter<any>();

  constructor() {
  }

  ngOnChanges(changes: SimpleChanges): void {
    console.log(this.foodData)
  }

}
