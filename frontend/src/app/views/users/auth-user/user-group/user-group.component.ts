import {Component, Input, OnChanges, SimpleChanges, ViewChild} from '@angular/core';
import {DxDataGridComponent} from "devextreme-angular";
import {UserService} from "../../../../services/user.service";
import CustomStore from "devextreme/data/custom_store";

@Component({
  selector: 'app-user-group',
  templateUrl: './user-group.component.html',
  styleUrls: ['./user-group.component.scss']
})
export class UserGroupComponent implements OnChanges {
  @Input() data: any;
  dataSource: any = {};
  groupDataSource: any = {};
  @ViewChild('dataSourceGrid', {static: true}) dataSourceGrid: any = DxDataGridComponent;
  events: Array<string> = [];
  userId: any = null;

  constructor(private service: UserService) {
    this.loadGrid = this.loadGrid.bind(this);
    this.loadGroup = this.loadGroup.bind(this);
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.userId = this.data ? this.data.id : null;
    if (this.userId !== null) {
      this.loadGrid();
      this.loadGroup();
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
        return this.service.getUserGroup(this.userId).toPromise().then((response: any) => {
          return {
            data: response.data,
            totalCount: response.totalCount
          };
        });
      },
      insert: (values) => {
        values.userId = this.userId;
        return this.service.joinUserGroup(values).toPromise().then((response) => {
            return;
          },
          err => {
            throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
          }
        );
      },
      remove: (key) => {
        const dto = {id: key, userId: this.userId};
        return this.service.leaveUserGroup(dto).toPromise().then((response) => {
            return;
          },
          err => {
            throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
          }
        );
      }

    });
  }

  loadGroup() {
    this.service.findAllGroup(null).toPromise().then((response: any) => {
      this.groupDataSource = response.data;
    });
  }

}