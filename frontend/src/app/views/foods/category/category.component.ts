import {Component, OnInit, ViewChild} from '@angular/core';
import {DxDataGridComponent} from "devextreme-angular";
import CustomStore from 'devextreme/data/custom_store';
import {Category} from "../../../services/food-service-api";
import {UtilService} from "../../../services/util.service";
import {GenericService} from "../../../services/generic.service";
import CategoryTypeEnum = Category.CategoryTypeEnum;

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit {
  dataSource: any = {};
  @ViewChild('categoryDataGrid', {static: true}) categoryDataGrid: any = DxDataGridComponent;
  events: Array<string> = [];
  categoryService: GenericService;
  dataTypeSource: any = [
    {name: CategoryTypeEnum.Sebze},
    {name: CategoryTypeEnum.Meyve},
    {name: CategoryTypeEnum.Kahvalti},
    {name: CategoryTypeEnum.Firin},
    {name: CategoryTypeEnum.Icecek},
    {name: CategoryTypeEnum.Atistirma},
  ];

  constructor(public service: GenericService) {
    this.categoryService = this.service.instance('foods/categorys');
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
        return this.categoryService.findAll(UtilService.setPage(loadOptions)).then((response: any) => {
          return {
            data: response.items,
            totalCount: response.totalCount
          };
        });
      },

      byKey: (key) => {
        return this.categoryService.findOne(key).then((response) => {
          return response;
        });
      },

      insert: (values) => {
        return this.categoryService.save(values).then((response) => {
            return;
          },
          err => {
            throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
          }
        );
      },
      update: (key, values: any) => {
        values.id = key;
        return this.categoryService.update(key, values).then((response) => {
            return;
          },
          err => {
            throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
          }
        );
      },
      remove: (key) => {
        return this.categoryService.delete(key).then((response) => {
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
