import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormDebtComponent } from './form-debt.component';

describe('FormDebtComponent', () => {
  let component: FormDebtComponent;
  let fixture: ComponentFixture<FormDebtComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormDebtComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormDebtComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
