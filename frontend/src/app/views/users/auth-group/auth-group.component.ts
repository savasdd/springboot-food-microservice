import {Component, OnInit, ViewChild} from '@angular/core';
import {DxDataGridComponent} from "devextreme-angular";
import {UserService} from "../../../services/user.service";
import CustomStore from "devextreme/data/custom_store";
import {faRefresh, faShoppingBasket} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-service-group',
  templateUrl: './auth-group.component.html',
  styleUrls: ['./auth-group.component.scss']
})
export class AuthGroupComponent implements OnInit {
  dataSource: any = {};
  @ViewChild('dataSourceGrid', {static: true}) dataSourceGrid: any = DxDataGridComponent;
  updateMod: boolean = false;
  data: any;

  constructor(private service: UserService) {
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
        return this.service.findAllGroup(loadOptions).toPromise().then((response: any) => {
          return {
            data: response.data,
            totalCount: response.totalCount
          };
        });
      },
      insert: (values) => {
        return this.service.saveGroup(values).toPromise().then((response) => {
            return;
          },
          err => {
            throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
          }
        );
      },
    });
  }

  protected readonly faShoppingBasket = faShoppingBasket;
  protected readonly faRefresh = faRefresh;
}
