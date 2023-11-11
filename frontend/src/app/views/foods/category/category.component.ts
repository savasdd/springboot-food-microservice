import {Component, OnInit, ViewChild} from '@angular/core';
import {CategoryService} from "../../../services/category.service";
import {DxDataGridComponent} from "devextreme-angular";
import CustomStore from 'devextreme/data/custom_store';
import {Category} from "../../../services/food-service-api";
import CategoryTypeEnum = Category.CategoryTypeEnum;
import {UtilService} from "../../../services/util.service";

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit {
  dataSource: any = {};
  @ViewChild('categoryDataGrid', {static: true}) categoryDataGrid: any = DxDataGridComponent;
  events: Array<string> = [];
  dataTypeSource: any = [
    {name: CategoryTypeEnum.Sebze},
    {name: CategoryTypeEnum.Meyve},
    {name: CategoryTypeEnum.Kahvalti},
    {name: CategoryTypeEnum.Firin},
    {name: CategoryTypeEnum.Icecek},
    {name: CategoryTypeEnum.Atistirma},
  ];

  constructor(public service: CategoryService) {
    this.loadGrid();
  }

  ngOnInit(): void {
  }

  logEvent(eventName: any) {
    this.events.unshift(eventName);
  }

  refreshDataGrid(e: any) {
    this.categoryDataGrid.instance.refresh();
  }

  loadGrid() {
    this.dataSource = new CustomStore({
      key: 'id',
      load: (loadOptions) => {
        return this.service.findAll(UtilService.setPage(loadOptions)).toPromise().then((response: any) => {
          return {
            data: response.items,
            totalCount: response.totalCount
          };
        });
      },

      byKey: (key) => {
        return this.service.findOne(key).toPromise().then((response) => {
          return response;
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
            throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
          }
        );
      },
      remove: (key) => {
        return this.service.delete(key).toPromise().then((response) => {
            return;
          },
          err => {
            throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
          }
        );
      }
    });
  }

}
