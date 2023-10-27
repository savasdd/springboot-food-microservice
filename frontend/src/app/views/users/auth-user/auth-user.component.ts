import {Component, OnInit, ViewChild} from '@angular/core';
import {DxDataGridComponent} from "devextreme-angular";
import {UserService} from "../../../services/user.service";
import CustomStore from "devextreme/data/custom_store";
import {faRefresh} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-auth-user',
  templateUrl: './auth-user.component.html',
  styleUrls: ['./auth-user.component.scss']
})
export class AuthUserComponent implements OnInit {
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
        return this.service.findAllUser(loadOptions).toPromise().then((response: any) => {
          return {
            data: response.data,
            totalCount: response.totalCount
          };
        });
      },
      insert: (values) => {
        return this.service.saveUser(values).toPromise().then((response) => {
            return;
          },
          err => {
            throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
          }
        );
      },
      remove: (key) => {
        return this.service.deleteUser(key).toPromise().then((response) => {
            return;
          },
          err => {
            const message = 'Kayıt Silme Hatası: ' + err.error.errorMessage;
            console.log(message);
          }
        );
      }
    });
  }

  protected readonly faRefresh = faRefresh;
}
