import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRegInstructuion } from '../reg-instructuion.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../reg-instructuion.test-samples';

import { RegInstructuionService } from './reg-instructuion.service';

const requireRestSample: IRegInstructuion = {
  ...sampleWithRequiredData,
};

describe('RegInstructuion Service', () => {
  let service: RegInstructuionService;
  let httpMock: HttpTestingController;
  let expectedResult: IRegInstructuion | IRegInstructuion[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RegInstructuionService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a RegInstructuion', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const regInstructuion = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(regInstructuion).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RegInstructuion', () => {
      const regInstructuion = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(regInstructuion).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RegInstructuion', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RegInstructuion', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a RegInstructuion', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRegInstructuionToCollectionIfMissing', () => {
      it('should add a RegInstructuion to an empty array', () => {
        const regInstructuion: IRegInstructuion = sampleWithRequiredData;
        expectedResult = service.addRegInstructuionToCollectionIfMissing([], regInstructuion);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(regInstructuion);
      });

      it('should not add a RegInstructuion to an array that contains it', () => {
        const regInstructuion: IRegInstructuion = sampleWithRequiredData;
        const regInstructuionCollection: IRegInstructuion[] = [
          {
            ...regInstructuion,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRegInstructuionToCollectionIfMissing(regInstructuionCollection, regInstructuion);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RegInstructuion to an array that doesn't contain it", () => {
        const regInstructuion: IRegInstructuion = sampleWithRequiredData;
        const regInstructuionCollection: IRegInstructuion[] = [sampleWithPartialData];
        expectedResult = service.addRegInstructuionToCollectionIfMissing(regInstructuionCollection, regInstructuion);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(regInstructuion);
      });

      it('should add only unique RegInstructuion to an array', () => {
        const regInstructuionArray: IRegInstructuion[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const regInstructuionCollection: IRegInstructuion[] = [sampleWithRequiredData];
        expectedResult = service.addRegInstructuionToCollectionIfMissing(regInstructuionCollection, ...regInstructuionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const regInstructuion: IRegInstructuion = sampleWithRequiredData;
        const regInstructuion2: IRegInstructuion = sampleWithPartialData;
        expectedResult = service.addRegInstructuionToCollectionIfMissing([], regInstructuion, regInstructuion2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(regInstructuion);
        expect(expectedResult).toContain(regInstructuion2);
      });

      it('should accept null and undefined values', () => {
        const regInstructuion: IRegInstructuion = sampleWithRequiredData;
        expectedResult = service.addRegInstructuionToCollectionIfMissing([], null, regInstructuion, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(regInstructuion);
      });

      it('should return initial array if no RegInstructuion is added', () => {
        const regInstructuionCollection: IRegInstructuion[] = [sampleWithRequiredData];
        expectedResult = service.addRegInstructuionToCollectionIfMissing(regInstructuionCollection, undefined, null);
        expect(expectedResult).toEqual(regInstructuionCollection);
      });
    });

    describe('compareRegInstructuion', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRegInstructuion(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareRegInstructuion(entity1, entity2);
        const compareResult2 = service.compareRegInstructuion(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareRegInstructuion(entity1, entity2);
        const compareResult2 = service.compareRegInstructuion(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareRegInstructuion(entity1, entity2);
        const compareResult2 = service.compareRegInstructuion(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
