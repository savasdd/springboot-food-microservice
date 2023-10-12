import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild} from '@angular/core';
import {DxDataGridComponent} from "devextreme-angular";
import {FoodService} from "../../../../services/food.service";
import {FoodFileDto} from "../../../../services/food-service-api";

@Component({
  selector: 'app-images',
  templateUrl: './images.component.html',
  styleUrls: ['./images.component.scss']
})
export class ImagesComponent implements OnChanges {
  @Input() foodData: any;
  @ViewChild('dataGrid', {static: true}) dataGrid: DxDataGridComponent | undefined;
  @Output() onHidingPopup: EventEmitter<any> = new EventEmitter<any>();
  dataSource: any = {};

  constructor(private service: FoodService) {

  }

  ngOnChanges(changes: SimpleChanges): void {
    const id = this.foodData ? this.foodData.foodId : null;
    this.service.getAllImage(id).subscribe((response: FoodFileDto[]) => {
      if (response) {
        this.dataSource = response;
      }
    });
  }

}
