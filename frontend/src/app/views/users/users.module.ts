import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {
  ButtonGroupModule,
  ButtonModule,
  CardModule,
  CollapseModule,
  DropdownModule,
  FormModule,
  GridModule,
  NavbarModule,
  NavModule,
  SharedModule,
  UtilitiesModule
} from "@coreui/angular";
import {IconModule} from "@coreui/icons-angular";
import {ReactiveFormsModule} from "@angular/forms";
import {DocsComponentsModule} from "@docs-components/docs-components.module";
import {RouterModule, Routes} from "@angular/router";
import {DevExtremeModule} from "devextreme-angular";
import {UserProfileComponent} from "./user-profile/user-profile.component";
import {AuthUserComponent} from './auth-user/auth-user.component';
import {AuthRolComponent} from './auth-rol/auth-rol.component';
import {AuthGroupComponent} from './auth-group/auth-group.component';
import { UserGroupComponent } from './auth-user/user-group/user-group.component';
import { GroupRolComponent } from './auth-group/group-rol/group-rol.component';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import { UserDepartmentComponent } from './auth-user/user-department/user-department.component';
import { UserLocationComponent } from './auth-user/user-location/user-location.component';
import { LocationMapComponent } from './auth-user/user-location/location-map/location-map.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'profile', component: UserProfileComponent,
        data: {
          title: 'Profile'
        }
      },
      {
        path: 'user', component: AuthUserComponent,
        data: {
          title: 'Users'
        }
      },
      {
        path: 'rol', component: AuthRolComponent,
        data: {
          title: 'Rols'
        }
      },
      {
        path: 'group', component: AuthGroupComponent,
        data: {
          title: 'Groups'
        }
      },
    ]
  }
];

@NgModule({
  declarations: [
    UserProfileComponent,
    AuthUserComponent,
    AuthRolComponent,
    AuthGroupComponent,
    UserGroupComponent,
    GroupRolComponent,
    UserDepartmentComponent,
    UserLocationComponent,
    LocationMapComponent,
  ],
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        ButtonModule,
        ButtonGroupModule,
        GridModule,
        IconModule,
        CardModule,
        UtilitiesModule,
        DropdownModule,
        SharedModule,
        FormModule,
        ReactiveFormsModule,
        DocsComponentsModule,
        NavbarModule,
        CollapseModule,
        NavModule,
        DevExtremeModule,
        FontAwesomeModule,
    ]
})
export class UsersModule {
}
