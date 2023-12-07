import { Component, Input, OnChanges, SimpleChanges, ViewChild } from '@angular/core';
import { DxDataGridComponent } from "devextreme-angular";
import CustomStore from "devextreme/data/custom_store";
import { GenericService } from "../../../../services/generic.service";
import { UtilService } from "../../../../services/util.service";

@Component({
  selector: 'app-user-group',
  templateUrl: './user-group.component.html',
  styleUrls: ['./user-group.component.scss']
})
export class UserGroupComponent implements OnChanges {
  @Input() data: any;
  dataSource: any = {};
  groupDataSource: any = {};
  @ViewChild('dataSourceGrid', { static: true }) dataSourceGrid: any = DxDataGridComponent;
  events: Array<string> = [];
  userId: any = null;
  userService: GenericService;
  groupService: GenericService;

  constructor(private service: GenericService) {
    this.loadGrid = this.loadGrid.bind(this);
    this.loadGroup = this.loadGroup.bind(this);
    this.userService = this.service.instance('auths/users');
    this.groupService = this.service.instance('auths/groups');

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
        return this.userService.customGet('custom/group/' + this.userId).then((response: any) => {
          return {
            data: response.data,
            totalCount: response.totalCount
          };
        });
      },
      insert: (values) => {
        values.userId = this.userId;
        return this.userService.customPost('custom/group', values).then((response) => {
          return;
        }
        );
      },
      remove: (key) => {
        const dto = { id: key, userId: this.userId };
        return this.userService.customPost('custom/group/leave', dto).then((response) => {
          return;
        }
        );
      }

    });
  }

  loadGroup() {
    this.groupService.findAll(UtilService.setPage({ skip: null, take: null })).then((response: any) => {
      this.groupDataSource = response.data;
    });
  }

}
