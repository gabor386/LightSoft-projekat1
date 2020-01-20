import { TestBed } from '@angular/core/testing';

import { StadingService } from './stading.service';

describe('StadingService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: StadingService = TestBed.get(StadingService);
    expect(service).toBeTruthy();
  });
});
