package com.flywet.platform.bi.pivot.model.olap.query;

import java.util.EventListener;

/**
 * @param quax the Quax being changed
 * @param source the initiator object of the change
 * @param changedMemberSet true if the member set was changed
 *                         by the navigator
 */
public interface QuaxChangeListener extends EventListener {
  void quaxChanged(Quax quax, Object source, boolean changedMemberSet);
} // QuaxChangedListener
