import {Component, Input, OnChanges, SimpleChanges, ViewChild} from '@angular/core';
import {DxDataGridComponent} from "devextreme-angular";
import CustomStore from "devextreme/data/custom_store";
import {DepartmentUserService} from "../../../../services/department-user.service";
import {DepartmentService} from "../../../../services/department.service";

@Component({
  selector: 'app-user-department',
  templateUrl: './user-department.component.html',
  styleUrls: ['./user-department.component.scss']
})
export class UserDepartmentComponent implements OnChanges {
  @Input() data: any;
  dataSource: any = {};
  departmentDataSource: any = {};
  @ViewChild('dataSourceGrid', {static: true}) dataSourceGrid: any = DxDataGridComponent;
  events: Array<string> = [];
  userId: any = null;
  dropDownOptions: any;

  constructor(private service: DepartmentUserService,
              private departService: DepartmentService) {
    // this.loadGrid = this.loadGrid.bind(this);
    // this.loadDepartment = this.loadDepartment.bind(this);
    this.loadDepartment();
    this.loadGrid();
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.userId = this.data ? this.data.id : null;
  }

  logEvent(eventName: any) {
    this.events.unshift(eventName);
  }

  refreshDataGrid(e: any) {
    this.dataSourceGrid.instance.refresh();
  }

  onSelectionPersonChanged(selectedRowKeys: any, cellInfo: any, dropDownBoxComponent: any) {
    console.log(selectedRowKeys[0])

    cellInfo.setValue(selectedRowKeys[0]);
    if (selectedRowKeys.length > 0) {
      dropDownBoxComponent.close();
    }
  }

  loadGrid() {
    this.dataSource = new CustomStore({
      key: 'id',
      load: (loadOptions) => {
        loadOptions.filter = [];
        loadOptions.filter.push(['userId', '=', this.userId]);
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
        });
      },

      insert: (values) => {
        values.userId = this.userId;
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
        values.userId = this.userId;
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

  loadDepartment() {
    this.departmentDataSource = new CustomStore({
      key: 'id',
      load: (loadOptions) => {
        return this.departService.findAll(loadOptions).toPromise().then((response: any) => {
          return {
            data: response.data,
            totalCount: response.totalCount
          };
        });
      },

      byKey: (key) => {
        return this.departService.findOne(key).toPromise().then((response) => {
          return response;
        });
      },
    });
  }
}
