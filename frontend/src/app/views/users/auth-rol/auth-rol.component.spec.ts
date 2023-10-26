import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthRolComponent } from './auth-rol.component';

describe('AuthRolComponent', () => {
  let component: AuthRolComponent;
  let fixture: ComponentFixture<AuthRolComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AuthRolComponent]
    });
    fixture = TestBed.createComponent(AuthRolComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
