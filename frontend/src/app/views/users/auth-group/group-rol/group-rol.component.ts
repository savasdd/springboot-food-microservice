import {Component, Input, OnChanges, SimpleChanges, ViewChild} from '@angular/core';
import {DxDataGridComponent} from "devextreme-angular";
import CustomStore from "devextreme/data/custom_store";
import {UserService} from "../../../../services/user.service";

@Component({
  selector: 'app-group-rol',
  templateUrl: './group-rol.component.html',
  styleUrls: ['./group-rol.component.scss']
})
export class GroupRolComponent implements OnChanges {
  @Input() data: any;
  dataSource: any = {};
  rolDataSource: any = {};
  @ViewChild('dataSourceGrid', {static: true}) dataSourceGrid: any = DxDataGridComponent;
  events: Array<string> = [];
  groupId: any = null;

  constructor(private service: UserService) {
    this.loadGrid = this.loadGrid.bind(this);
    this.loadRol = this.loadRol.bind(this);
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.groupId = this.data ? this.data.id : null;
    if (this.groupId !== null) {
      this.loadGrid();
      this.loadRol();
    }
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
        return this.service.getGroupRol(this.groupId).toPromise().then((response: any) => {
          return {
            data: response.data,
            totalCount: response.totalCount
          };
        });
      },
      insert: (values) => {
        values.groupId = this.groupId;
        return this.service.addGroupRol(values).toPromise().then((response) => {
            return;
          },
          err => {
            throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
          }
        );
      },
      remove: (key) => {
        const dto = {id: key, groupId: this.groupId};
        return this.service.leaveGroupRol(dto).toPromise().then((response) => {
            return;
          },
          err => {
            throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
          }
        );
      }

    });
  }


  loadRol() {
    this.service.findAllRoll(null).toPromise().then((response: any) => {
      this.rolDataSource = response.data;
    });
  }

}
