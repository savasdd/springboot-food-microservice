import { Component, Input, OnChanges, SimpleChanges, ViewChild } from '@angular/core';
import { DxDataGridComponent } from "devextreme-angular";
import CustomStore from "devextreme/data/custom_store";
import { UtilService } from "../../../../services/util.service";
import { GenericService } from "../../../../services/generic.service";

@Component({
  selector: 'app-user-department',
  templateUrl: './user-department.component.html',
  styleUrls: ['./user-department.component.scss']
})
export class UserDepartmentComponent implements OnChanges {
  @Input() data: any;
  dataSource: any = {};
  departmentDataSource: any = {};
  @ViewChild('dataSourceGrid', { static: true }) dataSourceGrid: any = DxDataGridComponent;
  events: Array<string> = [];
  userId: any = null;
  departService: GenericService;
  departUserService: GenericService;

  constructor(private service: GenericService) {
    this.loadGrid = this.loadGrid.bind(this);
    this.loadDepartment = this.loadDepartment.bind(this);
    this.departService = this.service.instance('foods/departments');
    this.departUserService = this.service.instance('foods/users/departments');
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
        return this.departUserService.findAll(UtilService.setPage(loadOptions)).then((response: any) => {
          return {
            data: response.items,
            totalCount: response.totalCount
          };
        });
      },

      byKey: (key) => {
        return this.departUserService.findOne(key).then((response: any) => {
          return response;
        });
      },

      insert: (values) => {
        values.userId = this.userId;
        return this.departUserService.save(values).then((response) => {
          return;
        }
        );
      },
      update: (key, values: any) => {
        values.id = key;
        values.userId = this.userId;
        return this.departUserService.update(key, values).then((response) => {
          return;
        }
        );
      },
      remove: (key) => {
        return this.departUserService.delete(key).then((response) => {
          return;
        }
        );
      }
    });
  }

  loadDepartment() {
    this.departmentDataSource = new CustomStore({
      key: 'id',
      load: (loadOptions) => {
        return this.departService.findAll(UtilService.setPage({ skip: null, take: null })).then((response: any) => {
          return {
            data: response.items,
            totalCount: response.totalCount
          };
        });
      },

      byKey: (key) => {
        return this.departService.findOne(key).then((response) => {
          return response;
        });
      },
    });
  }
}
