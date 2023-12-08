import {Component, OnInit, ViewChild} from '@angular/core';
import {DxDataGridComponent} from "devextreme-angular";
import CustomStore from "devextreme/data/custom_store";
import {UtilService} from "../../../services/util.service";
import {GenericService} from "../../../services/generic.service";

@Component({
  selector: 'app-department',
  templateUrl: './department.component.html',
  styleUrls: ['./department.component.scss']
})
export class DepartmentComponent implements OnInit {
  dataSource: any = {};
  @ViewChild('dataGrid', {static: true}) dataGrid: any = DxDataGridComponent;
  departmentService: GenericService;

  constructor(private service: GenericService) {
    this.departmentService = this.service.instance('foods/departments');
    this.loadGrid();
  }

  ngOnInit(): void {
  }

  initNewRow(e: any) {
    e.data.Task_Status = 'Not Started';
    e.data.Task_Start_Date = new Date();
    e.data.Task_Due_Date = new Date();
  }

  refreshDataGrid(e: any) {
    this.dataGrid.instance.refresh();
  }

  loadGrid() {
    this.dataSource = new CustomStore({
      key: 'id',
      load: (loadOptions) => {
        return this.departmentService.findAll(UtilService.setPage(loadOptions)).then((response: any) => {
          return {
            data: response.items,
            totalCount: response.totalCount
          };
        });
      },

      byKey: (key) => {
        return this.departmentService.findOne(key).then((response) => {
          return response;
        });
      },

      insert: (values) => {
        return this.departmentService.save(values).then((response) => {
            return;
          }
        );
      },
      update: (key, values: any) => {
        values.id = key;
        return this.departmentService.update(key, values).then((response) => {
            return;
          }
        );
      },
      remove: (key) => {
        return this.departmentService.delete(key).then((response) => {
            return;
          }
        );
      }
    });
  }

}
