import {Component, OnInit, ViewChild} from '@angular/core';
import {CategoryService} from "../../../services/category.service";
import {DxDataGridComponent} from "devextreme-angular";
import CustomStore from 'devextreme/data/custom_store';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit {
  dataSource: any = {};
  @ViewChild('categoryDataGrid', {static: true}) categoryDataGrid: DxDataGridComponent | undefined;
  events: Array<string> = [];

  constructor(public service: CategoryService) {
    this.loadGrid();
  }

  ngOnInit(): void {
  }

  logEvent(eventName: any) {
    this.events.unshift(eventName);
  }

  loadGrid() {
    this.dataSource = new CustomStore({
      key: 'id',
      load: (loadOptions) => {
        return this.service.findAll(loadOptions).toPromise().then((response: any) => {
          console.log(response)
          return {
            data: response.data,
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
          }
        );
      },
      remove: (key) => {
        return this.service.delete(key).toPromise().then((response) => {
            return;
          },
          err => {
          }
        );
      }
    });
  }

}
