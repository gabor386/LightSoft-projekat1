import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FixturesnsComponent } from './fixturesns.component';

describe('FixturesnsComponent', () => {
  let component: FixturesnsComponent;
  let fixture: ComponentFixture<FixturesnsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FixturesnsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FixturesnsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
