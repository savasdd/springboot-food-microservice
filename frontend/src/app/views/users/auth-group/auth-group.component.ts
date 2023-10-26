import {Component, OnInit, ViewChild} from '@angular/core';
import {DxDataGridComponent} from "devextreme-angular";
import {UserService} from "../../../services/user.service";
import CustomStore from "devextreme/data/custom_store";

@Component({
  selector: 'app-auth-group',
  templateUrl: './auth-group.component.html',
  styleUrls: ['./auth-group.component.scss']
})
export class AuthGroupComponent implements OnInit {
  dataSource: any = {};
  @ViewChild('dataSourceGrid', {static: true}) dataSourceGrid: any = DxDataGridComponent;
  events: Array<string> = [];

  constructor(private service: UserService) {
    this.loadGrid();
  }

  ngOnInit(): void {
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
        return this.service.findAllGroup(loadOptions).toPromise().then((response: any) => {
          return {
            data: response,
            totalCount: 200
          };
        });
      },
    });
  }
}
