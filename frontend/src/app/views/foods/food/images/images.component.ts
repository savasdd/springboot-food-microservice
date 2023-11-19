import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild} from '@angular/core';
import {DxDataGridComponent, DxFormComponent} from "devextreme-angular";
import {FoodFileDto} from "../../../../services/food-service-api";
import notify from "devextreme/ui/notify";
import {GenericService} from "../../../../services/generic.service";

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
  fileData: FoodFileDto = {};
  foodId: any;
  foodService: GenericService;

  constructor(private service: GenericService) {
    this.foodService = this.service.instance('foods/file');

  }

  ngOnChanges(changes: SimpleChanges): void {
    this.loadData();
  }

  logEvent(eventName: any) {
    this.events.unshift(eventName);
  }

  newImages() {
    this.popupVisible = true;
  }

  refreshDataGrid(e: any) {
    this.loadData();
    this.dataGrid.instance.refresh();
  }

  loadData() {
    if (this.foodData.id) {
      this.foodId = this.foodData ? this.foodData.id : null;
      this.foodService.customGet('/all/' + this.foodId).then((response: any) => {
        if (response) {
          this.dataSource = response;
        }
      });
    }
  }

  deleteFoodImage(data: any) {
    if (data.foodId) {
      this.foodService.customDelete('/delete/' + data.foodId).then((response: any) => {
        this.loadData();
      });
    }
  }

  onValueChanged(e: any) {
    const file = e.value[0];
    const fileReader = new FileReader();
    this.fileData.filename = file.name;
    this.fileData.fileType = file.type;

    fileReader.onload = () => {
      this.fileData.fileData = new Blob([fileReader.result as ArrayBuffer], {type: file.type});
    }
    fileReader.readAsDataURL(file);
  }

  updateClick() {
    const formValid = this.form.instance.validate();
    if (formValid && this.fileData.fileData != null) {
      this.foodService.fileUpload('upload', this.foodId, this.fileData.filename, this.fileData.fileType, this.fileData.fileData).subscribe((response: any) => {
        notify({message: "Upload Success"});
        this.popupVisible = false;
        this.loadData();
      });
    }
  }


}
