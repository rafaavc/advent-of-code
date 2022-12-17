import numpy as np


def merge_without_sort(segments):
    # merge overlapping segments
    merger_segment = 0

    while merger_segment < len(segments) - 1:
        segment_ = segments[merger_segment + 1]
        merger_segment_ = segments[merger_segment]
        if segment_[0] <= merger_segment_[1] + 1:
            if segment_[1] > merger_segment_[1]:
                merger_segment_[1] = segment_[1]
            del segments[merger_segment + 1]
        else:
            merger_segment += 1
    return segments


def merge_segments(segments):
    segments.sort()
    return merge_without_sort(segments)


def add_segment_to_sorted_segments(segments, segment):
    for i, s in enumerate(segments):
        if segment[0] < s[0]:
            segments.insert(i, segment)
            return
    segments.append(segment)


def add_segment_by_merging(segments, new_segment):
    add_segment_to_sorted_segments(segments, new_segment)
    return merge_without_sort(segments)


def nparray_equals(a1, a2):
    return (np.array(a1) == np.array(a2)).all()


def test_merge_segments():
    assert nparray_equals(merge_segments([[1, 2], [2, 3]]), [[1, 3]])
    assert nparray_equals(merge_segments([[1, 2], [3, 4]]), [[1, 4]])
    assert nparray_equals(merge_segments([[1, 4], [3, 3]]), [[1, 4]])
    assert nparray_equals(merge_segments([[1, 4], [6, 7]]), [[1, 4], [6, 7]])
    assert nparray_equals(merge_segments([[1, 4], [4, 5], [10, 15]]), [[1, 5], [10, 15]])
    assert nparray_equals(merge_segments([[-5, 5], [1, 4], [4, 5], [10, 15]]), [[-5, 5], [10, 15]])


def test_add_segment_by_merging():
    assert nparray_equals(add_segment_by_merging([[1, 4], [6, 7]], [5, 5]), [[1, 7]])
    assert nparray_equals(add_segment_by_merging([[1, 4], [6, 7]], [8, 9]), [[1, 4], [6, 9]])
    assert nparray_equals(add_segment_by_merging([[3, 4], [6, 7]], [1, 2]), [[1, 4], [6, 7]])
    assert nparray_equals(add_segment_by_merging([[3, 4], [10, 15]], [6, 8]), [[3, 4], [6, 8], [10, 15]])
    assert nparray_equals(add_segment_by_merging([[3, 4], [10, 15]], [-6, -1]), [[-6, -1], [3, 4], [10, 15]])
    assert nparray_equals(add_segment_by_merging([[3, 4], [10, 15]], [-6, 3]), [[-6, 4], [10, 15]])
    assert nparray_equals(add_segment_by_merging([[3, 4], [10, 15], [17, 199]], [-6, 500]), [[-6, 500]])


test_merge_segments()
test_add_segment_by_merging()
