import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StadingComponent } from './stading.component';

describe('StadingComponent', () => {
  let component: StadingComponent;
  let fixture: ComponentFixture<StadingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StadingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StadingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
