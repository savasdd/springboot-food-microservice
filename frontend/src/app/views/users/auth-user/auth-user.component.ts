import {Component, OnInit, ViewChild} from '@angular/core';
import {DxDataGridComponent} from "devextreme-angular";
import CustomStore from "devextreme/data/custom_store";
import {faRefresh} from "@fortawesome/free-solid-svg-icons";
import {GenericService} from "../../../services/generic.service";
import {UtilService} from "../../../services/util.service";

@Component({
  selector: 'app-service-user',
  templateUrl: './auth-user.component.html',
  styleUrls: ['./auth-user.component.scss']
})
export class AuthUserComponent implements OnInit {
  dataSource: any = {};
  @ViewChild('dataSourceGrid', {static: true}) dataSourceGrid: any = DxDataGridComponent;
  updateMod: boolean = false;
  data: any;
  userService: GenericService;
  tabList: any[] = [
    {id: 1, name: 'Group', key: 'information'},
    {id: 2, name: "Department", key: 'action'},
    {id: 3, name: "Location", key: 'location'},
  ];

  constructor(private service: GenericService) {
    this.userService = this.service.instance('auths/users');
    this.loadGrid();
  }

  ngOnInit(): void {
  }

  onEditorPreparing(e: any) {
    if (e.parentType === 'dataRow') {
      this.data = e.row.data;
      this.updateMod = e.row.data.id != undefined;
    }
  }


  refreshDataGrid(e: any) {
    this.dataSourceGrid.instance.refresh();
  }

  loadGrid() {
    this.dataSource = new CustomStore({
      key: 'id',
      load: (loadOptions) => {
        return this.userService.findAll(UtilService.setPage(loadOptions)).then((response: any) => {
          return {
            data: response.data,
            totalCount: response.totalCount
          };
        });
      },
      insert: (values) => {
        return this.userService.save(values).then((response) => {
            return;
          }
        );
      },
      remove: (key) => {
        return this.userService.delete(key).then((response) => {
            return;
          }
        );
      }
    });
  }

  protected readonly faRefresh = faRefresh;
}
