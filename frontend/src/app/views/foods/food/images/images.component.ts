import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild} from '@angular/core';
import {DxDataGridComponent, DxFormComponent} from "devextreme-angular";
import {FoodService} from "../../../../services/food.service";
import {FoodFileDto} from "../../../../services/food-service-api";
import notify from "devextreme/ui/notify";

@Component({
  selector: 'app-images',
  templateUrl: './images.component.html',
  styleUrls: ['./images.component.scss']
})
export class ImagesComponent implements OnChanges {
  @Input() foodData: any;
  @ViewChild('dataGrid', {static: true}) dataGrid: any = DxDataGridComponent;
  @Output() onHidingPopup: EventEmitter<any> = new EventEmitter<any>();
  @ViewChild(DxFormComponent, {static: false}) form: any = DxFormComponent;

  dataSource: any = {};
  events: Array<string> = [];
  popupVisible: boolean = false;
  userData: FoodFileDto = {};
  foodId: any;

  constructor(private service: FoodService) {

  }

  ngOnChanges(changes: SimpleChanges): void {

    if (this.foodData.foodId) {
      this.foodId = this.foodData ? this.foodData.foodId : null;
      this.service.getAllImage(this.foodId).subscribe((response: FoodFileDto[]) => {
        if (response) {
          this.dataSource = response;
        }
      });
    }
  }

  logEvent(eventName: any) {
    this.events.unshift(eventName);
  }

  newImages() {
    this.popupVisible = true;
  }

  refreshDataGrid(e: any) {
    this.dataGrid.instance.refresh();
  }

  onValueChanged(e: any) {
    const file = e.value[0];
    const fileReader = new FileReader();
    fileReader.onload = () => {
      this.userData.fileData = new Blob([fileReader.result as ArrayBuffer], {type: file.type});
    }
    fileReader.readAsDataURL(file);
  }

  updateClick() {
    const formValid = this.form.instance.validate();
    if (formValid && this.userData.fileData != null) {
      this.service.uploadImage(this.foodId, this.userData.fileData).subscribe((response: any) => {
        notify({message: "Upload Success"});
        this.popupVisible = false;
      });
    }
  }


}
