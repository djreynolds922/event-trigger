package gg.xp.xivsupport.events.state;

import gg.xp.reevent.events.Event;
import gg.xp.reevent.events.EventContext;
import gg.xp.xivsupport.events.actlines.events.RawAddCombatantEvent;
import gg.xp.xivsupport.events.actlines.events.RawPlayerChangeEvent;
import gg.xp.xivsupport.events.actlines.events.RawRemoveCombatantEvent;
import gg.xp.xivsupport.events.actlines.events.ZoneChangeEvent;
import gg.xp.reevent.scan.HandleEvents;

@SuppressWarnings("UtilityClassWithoutPrivateConstructor")
public final class TrackStateChanges {

	@HandleEvents(order = Integer.MIN_VALUE)
	public static void zoneChange(EventContext<Event> context, ZoneChangeEvent event) {
		context.getStateInfo().get(XivState.class).setZone(event.getZone());
	}

	@HandleEvents(order = Integer.MIN_VALUE)
	public static void playerChange(EventContext<Event> context, RawPlayerChangeEvent event) {
		context.getStateInfo().get(XivState.class).setPlayer(event.getPlayer());
		// After learning about the player, make sure we request combatant data
		context.accept(new RefreshCombatantsRequest());
	}

	@HandleEvents(order = Integer.MIN_VALUE)
	public static void combatantAdded(EventContext<Event> context, RawAddCombatantEvent event) {
		context.accept(new RefreshCombatantsRequest());
	}

	@HandleEvents(order = Integer.MIN_VALUE)
	public static void combatantRemoved(EventContext<Event> context, RawRemoveCombatantEvent event) {
		context.accept(new RefreshCombatantsRequest());
	}

	@HandleEvents(order = Integer.MIN_VALUE)
	public static void partyChange(EventContext<Event> context, PartyChangeEvent event) {
		context.getStateInfo().get(XivState.class).setPartyList(event.getMembers());
	}

	@HandleEvents(order = Integer.MIN_VALUE)
	public static void combatants(EventContext<Event> context, CombatantsUpdateRaw event) {
		context.getStateInfo().get(XivState.class).setCombatants(event.getCombatantMaps());
	}

}