import {Component, OnInit, ViewChild} from '@angular/core';
import {DxDataGridComponent} from "devextreme-angular";
import {FoodService} from "../../../services/food.service";
import CustomStore from "devextreme/data/custom_store";

@Component({
  selector: 'app-food',
  templateUrl: './food.component.html',
  styleUrls: ['./food.component.scss']
})
export class FoodComponent implements OnInit {
  dataSource: any = {};
  @ViewChild('foodDataGrid', {static: true}) foodDataGrid: DxDataGridComponent | undefined;

  constructor(private service: FoodService) {
    this.loadGrid();
  }

  ngOnInit(): void {
  }

  loadGrid() {
    this.dataSource = new CustomStore({
      key: 'foodId',
      load: (loadOptions) => {
        return this.service.findAll(loadOptions).toPromise().then((response: any) => {
          return {
            data: response.data,
            totalCount: response.totalCount
          };
        });
      },

      byKey: (key) => {
        return this.service.findOne(key).toPromise().then((response) => {
          return response;
        }, err => {
          throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
        });
      },

      insert: (values) => {
        return this.service.save(values).toPromise().then((response) => {
            return;
          },
          err => {
            throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
          }
        );
      },
      update: (key, values: any) => {
        values.id = key;
        return this.service.update(key, values).toPromise().then((response) => {
            return;
          },
          err => {
            const message = 'Kayıt Güncelleme Hatası: ' + err.error.errorMessage;
            console.log(message);
          }
        );
      },
      remove: (key) => {
        return this.service.delete(key).toPromise().then((response) => {
            return;
          },
          err => {
            const message = 'Kayıt Silme Hatası: ' + err.error.errorMessage;
            console.log(message);
          }
        );
      }
    });
  }

}
