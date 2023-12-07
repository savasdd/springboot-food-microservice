import { Component, Input, OnChanges, SimpleChanges, ViewChild } from '@angular/core';
import { DxDataGridComponent } from "devextreme-angular";
import CustomStore from "devextreme/data/custom_store";
import { GenericService } from "../../../../services/generic.service";
import { UtilService } from "../../../../services/util.service";

@Component({
  selector: 'app-group-rol',
  templateUrl: './group-rol.component.html',
  styleUrls: ['./group-rol.component.scss']
})
export class GroupRolComponent implements OnChanges {
  @Input() data: any;
  dataSource: any = {};
  rolDataSource: any = {};
  @ViewChild('dataSourceGrid', { static: true }) dataSourceGrid: any = DxDataGridComponent;
  events: Array<string> = [];
  groupId: any = null;
  groupService: GenericService;
  roleService: GenericService;

  constructor(private service: GenericService) {
    this.loadGrid = this.loadGrid.bind(this);
    this.loadRol = this.loadRol.bind(this);
    this.groupService = this.service.instance('auths/groups');
    this.roleService = this.service.instance('auths/roles');

    this.loadGrid();
    this.loadRol();
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.groupId = this.data ? this.data.id : null;

  }

  logEvent(eventName: any) {
    this.events.unshift(eventName);
  }

  refreshDataGrid(e: any) {
    this.dataSourceGrid.instance.refresh();
  }

  loadGrid() {
    this.dataSource = new CustomStore({
      key: 'id',
      load: (loadOptions) => {
        return this.groupService.customGet('custom/role/' + this.groupId).then((response: any) => {
          return {
            data: response.data,
            totalCount: response.totalCount
          };
        });
      },
      insert: (values) => {
        values.groupId = this.groupId;
        values.name = this.filterRol(values.id);
        return this.groupService.customPost('custom/role', values).then((response) => {
          return;
        }
        );
      },
      remove: (key) => {
        const dto = { id: key, groupId: this.groupId, name: this.filterRol(key) };
        return this.groupService.customPost('custom/role/leave', dto).then((response) => {
          return;
        }
        );
      }

    });
  }


  loadRol() {
    this.roleService.findAll(UtilService.setPage({ skip: null, take: null })).then((response: any) => {
      this.rolDataSource = response.data;
    });
  }

  filterRol(id: string): any {
    let result;
    if (this.rolDataSource) {
      result = this.rolDataSource.filter((s: any) => s.id === id);
    }

    return result.length > 0 ? result[0].name : null;
  }

}
