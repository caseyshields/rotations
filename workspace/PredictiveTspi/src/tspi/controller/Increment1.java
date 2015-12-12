package tspi.controller;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;

import rotation.Vector3;
import tspi.model.Pedestal;
import tspi.model.PedestalModel;
import tspi.model.Solution;
import tspi.model.Target;
import tspi.model.TargetModel;

/** A controller for manipulating a set of pedestals and their targets. Meant 
 * to demonstrate the deliverables of Increment 1 of the Predictive TSPI 
 * project. */
@SuppressWarnings("serial")
public class Increment1 extends JFrame 
implements ActionListener, ListSelectionListener, TableModelListener {
	
	protected PedestalModel pedestals;
	protected TargetModel targets;
	protected JTable pedestalTable;
	protected JTable targetTable;
	protected JMenuItem addPedestal;
	protected JMenuItem addTarget;
	protected JMenuItem removePedestal;
	protected JMenuItem removeTarget;
	protected JMenuItem loadPedestals;
	protected JMenuItem loadTargets;
	protected JMenuItem savePedestals;
	protected JMenuItem saveTargets;
	protected JMenuItem coordGeocentric;
	protected JMenuItem coordEllipsoidal;
	protected DefaultTableCellRenderer cell;
	
	public static void main(String args[]) {
		Increment1 frame = new Increment1();
		if(args.length==2) {
			
			// try to load a default pedestal file using the first argument as a path
			File pedestalFile = new File( args[0] );
			try { frame.pedestals.load( pedestalFile ); }
			catch(Exception exception) {
				JOptionPane.showMessageDialog(frame, "Failed to load "+args[1]+" as a pedestal csv:\n"+ exception.getMessage());
			}
			
			// try to load a default target file using the second argument as a path
			File targetFile = new File( args[1] );
			try { frame.targets.load( targetFile ); }
			catch(Exception exception) {
				JOptionPane.showMessageDialog(frame, "Failed to load "+args[1]+" as a taget csv:\n" + exception.getMessage());
			}
		}
		frame.setVisible(true);
	}
	
	public Increment1() {
		
		pedestals = new PedestalModel();
		pedestals.addTableModelListener( this );
		
		pedestalTable = new JTable(pedestals);
		pedestalTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		pedestalTable.setRowSelectionAllowed(true);
		pedestalTable.setColumnSelectionAllowed(false);
		pedestalTable.createDefaultColumnsFromModel();
		pedestalTable.getSelectionModel().addListSelectionListener( this );
		
		this.cell = new PedestalModel.CellRenderer();
		pedestalTable.setDefaultRenderer(Double.class, cell);
		pedestalTable.setDefaultRenderer(String.class, cell);
		//pedestalTable.getColumnModel().getColumn(0).setCellRenderer(cell);
		
		targets = new TargetModel();
		targets.addTableModelListener( this );
		
		targetTable = new JTable( targets );
		targetTable.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		targetTable.setRowSelectionAllowed( true );
		targetTable.setColumnSelectionAllowed( false );
		targetTable.createDefaultColumnsFromModel();
		targetTable.getSelectionModel().addListSelectionListener( this );
		
		this.cell = new TargetModel.CellRenderer();
		targetTable.setDefaultRenderer(Double.class, cell);
		targetTable.setDefaultRenderer(String.class, cell);
		//targetTable.getColumnModel().getColumn(0).setCellRenderer(cell);
		
		JPanel pnlPedestals = new JPanel( new BorderLayout() );
		JLabel lblPedestals = new JLabel("Pedestals");
		lblPedestals.setHorizontalAlignment(JLabel.CENTER);
		JScrollPane scrPedestals = new JScrollPane(pedestalTable);
		pnlPedestals.add(lblPedestals, BorderLayout.NORTH);
		pnlPedestals.add(scrPedestals, BorderLayout.CENTER);
		
		JPanel pnlTarget = new JPanel( new BorderLayout() );
		JLabel lblTarget = new JLabel("Targets");
		lblTarget.setHorizontalAlignment(JLabel.CENTER);
		JScrollPane scrTarget = new JScrollPane(targetTable);
		pnlTarget.add(lblTarget, BorderLayout.NORTH);
		pnlTarget.add(scrTarget, BorderLayout.CENTER);
		
		JSplitPane split = new JSplitPane(
				JSplitPane.VERTICAL_SPLIT, true, pnlPedestals, pnlTarget);
		split.setResizeWeight(0.5);
		split.setDividerLocation(0.5);
		
		addPedestal = new JMenuItem("Add Pedestal");
		addTarget = new JMenuItem("Add Target");
		removePedestal = new JMenuItem("Remove Pedestal");
		removeTarget = new JMenuItem("Remove Target");
		addPedestal.addActionListener(this);
		addTarget.addActionListener(this);
		removePedestal.addActionListener(this);
		removeTarget.addActionListener(this);
		JMenu edit = new JMenu("Edit");
		edit.add(addPedestal);
		edit.add(addTarget);
		edit.add(removePedestal);
		edit.add(removeTarget);
		
		loadPedestals = new JMenuItem( "Load Pedestals" );
		loadTargets = new JMenuItem( "Load Targets" );
		loadPedestals.addActionListener(this);
		loadTargets.addActionListener(this);
		savePedestals = new JMenuItem( "Save Pedestals" );
		saveTargets = new JMenuItem( "Save Targets" );
		savePedestals.addActionListener(this);
		saveTargets.addActionListener(this);
		JMenu file = new JMenu("File");
		file.add(loadPedestals);
		file.add(loadTargets);
		file.add(savePedestals);
		file.add(saveTargets);
		
		coordEllipsoidal = new JMenuItem("Ellipsoidal");
		coordEllipsoidal.addActionListener(this);
		coordGeocentric = new JMenuItem("Geocentric");
		coordGeocentric.addActionListener(this);
		JMenu coordinates = new JMenu("Coordinates");
		coordinates.add(coordEllipsoidal);
		coordinates.add(coordGeocentric);
		
		//TODO add or remove systems and targets
		//JMenu edit = new JMenu("Edit");
		
		JMenuBar bar = new JMenuBar();
		bar.add(file);
		bar.add(edit);
		bar.add(coordinates);
		
		this.setLayout( new BorderLayout() );
		this.add(bar, BorderLayout.NORTH);
		this.add(split, BorderLayout.CENTER);
		this.setTitle("TSPI Predictor; Increment 1");
		this.setBounds(100, 100, 600, 400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	/** Increment 1, usecase 1: Update all pedestal angles to point to the given target*/
	public void ComputeDirections(Target target, PedestalModel pedestals) {
		Vector3 geo = target.getGeocentricCoordinates();
		if(geo==null) { //pedestal location not created[?]
			//System.out.println("ComputeDirections(): Invalid Target Coordinates( "+target.getTime()+", "+target.getLatitude()+", "+target.getLongitude()+", "+target.getHeight()+")");
			return;
		}
		for(Pedestal pedestal : pedestals)
			pedestal.point( geo );
		
		// clear error info
		this.targets.clearSolutions();
		
		// notify all listeners
		this.targets.fireTableRowsUpdated(0, targets.getRowCount()-1);
		this.pedestals.fireTableDataChanged(); // this listener update method also unselects previous selections
	}
	
	/** Increment 1, usecase 2: Updates the error of all targets using the two pedestals. */
	public void ComputeError(ArrayList<Pedestal> selected, TargetModel targets) {
		// for each target
		for(Target target : targets) {
			Vector3 geo = target.getGeocentricCoordinates();
			if(geo==null) {
				//System.out.println("ComputeError(): Invalid Target Coordinates( "+target.getTime()+", "+target.getLatitude()+", "+target.getLongitude()+", "+target.getHeight()+")");
				continue;
			}
			
			//Debug
			System.out.println( "\nTarget "+target.getTime()+" : " 
					+ " lon="+target.getEllipsoidalCoordinates().getEastLongitude().getDegrees()
					+ " lat="+target.getEllipsoidalCoordinates().getNorthLatitude().getDegrees()
					+ " h="+target.getHeight());
			
			// point pedestals to target
			for(Pedestal pedestal : selected)
				pedestal.point(geo);
			
			// TODO obtain the origin from somewhere instead of just using the first pedestal!
			Vector3 origin = new Vector3( selected.get(0).getGeocentricCoordinates() );
			
			// compute new target and measure error
			Solution solution = new Solution( origin, selected );
			solution.measureError( target.getGeocentricCoordinates() );
			target.setSolution(solution);
			
//			// set error in the corresponding target 
//			Vector3 targetPrime;
//			if( solution==null || solution.position_EFG==null )
//				targetPrime = new Vector3(0.0,0.0,0.0);
//			else targetPrime = solution.position_EFG;
//			targetPrime.subtract( target.getGeocentricCoordinates() );
//			double error = targetPrime.getAbs();
//			target.setError(error);
//			// TODO display more conditioning and error ellipse info, maybe even the calculated target location.
		}
		
		// clear pedestal heading data
		this.pedestals.clearOrientations();
		
		// notify listeners the data changed
		this.targets.fireTableDataChanged(); // this listener update method also unselects previous selections
		this.pedestals.fireTableRowsUpdated(0, pedestals.getRowCount()-1);

	}
	
	/** Pedestal and Target table selection listener. */
	@Override
	public void valueChanged(ListSelectionEvent event) {
		//don't do anything until the user is done highlighting
		if( event.getValueIsAdjusting() )
			return;
		
		// Pedestals were selected
		// The bounds of the selected interval are used as the input pedestals to compute error
		if( event.getSource() == this.pedestalTable.getSelectionModel() ) {
			
			int rows[] = pedestalTable.getSelectedRows();
			ArrayList<Pedestal> list = new ArrayList<Pedestal>();
			for(int row : rows) {
				if(row==-1) continue;
				Pedestal pedestal = pedestals.getPedestal(row); //pedestalTable.getRowSorter().convertRowIndexToModel(row) );
				list.add(pedestal);
			}
			
			// make sure enough pedestals are selected for a solution
			if(list.size() >= 2) {
				// deselect the target table, and clear the error deltas
				//targetTable.getSelectionModel().clearSelection();
				targets.clearSolutions();

				ComputeError(list, targets);
			}

		// a target was selected
		} else if( event.getSource() == this.targetTable.getSelectionModel() ) {
			// deselect the pedestal table
			//pedestalTable.getSelectionModel().clearSelection();
			
			// get the currently selected target
			int row = targetTable.getSelectionModel().getMinSelectionIndex();
			//row = targetTable.getRowSorter().convertRowIndexToModel(row);
			if(row==-1)
				return;
			Target target = targets.getTarget(row);
			
			//System.out.println("Point:\n"+target+"\n\n");
			
			// point all pedestals to the selected target
			ComputeDirections(target, pedestals);
		}
	}
	
	/** table edit listener */
	@Override
	public void tableChanged(TableModelEvent event) {
		// TODO for avoiding incorrect numbers that have to be fixed by clicking around;
		// on target edit, should recompute pedestal angles
		// on pedestal angle, should clear target error
	}
	
	/** Menu Item listener. */
	@Override
	public void actionPerformed(ActionEvent event) {
		try{
			if( event.getSource() == this.addPedestal ) {
				int index = this.pedestalTable.getSelectedRow();
				if(index==-1)
					index = this.pedestals.getRowCount();
				Pedestal pedestal = new Pedestal("",0.0,0.0,0.0);
				this.pedestals.add(index, pedestal);
				this.pedestalTable.setRowSelectionInterval(index, index);
				
			} else if( event.getSource() == this.addTarget ) {
				int index = this.targetTable.getSelectedRow();
				if(index==-1)
					index = this.targets.getRowCount();
				Target target = new Target(0L,0.0,0.0,0.0);
				this.targets.add(index, target);
				this.targetTable.setRowSelectionInterval(index, index);
			
			} else if( event.getSource() == this.removePedestal ) {
				int index = this.pedestalTable.getSelectedRow();
				if(index!=-1) {
					this.pedestals.remove(index);
					this.pedestalTable.clearSelection();
				} else
					JOptionPane.showMessageDialog(this, "Please select a pedestal.", "Selection needed", JOptionPane.INFORMATION_MESSAGE);

			} else if( event.getSource() == this.removeTarget ) {
				int index = this.targetTable.getSelectedRow();
				if(index!=-1) {
					this.targets.remove(index);
					this.targetTable.clearSelection();
				} else
					JOptionPane.showMessageDialog(this, "Please select a target.", "Selection needed", JOptionPane.INFORMATION_MESSAGE);

			} else if( event.getSource() == this.loadPedestals ) {
				JFileChooser chooser = new JFileChooser();
				if(JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(this)) {
					File file = chooser.getSelectedFile();
					this.pedestals.load(file);
				}
				
			} else if( event.getSource() == this.loadTargets ) {
				JFileChooser chooser = new JFileChooser();
				if(JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(this)) {
					File file = chooser.getSelectedFile();
					this.targets.load(file);
				}
				
			} else if( event.getSource() == this.savePedestals ) {
				JFileChooser chooser = new JFileChooser();
				if(JFileChooser.APPROVE_OPTION == chooser.showSaveDialog(this)) {
					File file = chooser.getSelectedFile();
					this.pedestals.save(file);
				}
				
			} else if( event.getSource() == this.saveTargets ) {
				JFileChooser chooser = new JFileChooser();
				if(JFileChooser.APPROVE_OPTION == chooser.showSaveDialog(this)) {
					File file = chooser.getSelectedFile();
					this.targets.save(file);
				}
				
			} else if( event.getSource()==this.coordEllipsoidal) {
				this.pedestals.setCooordinateSystem(PedestalModel.ELLIPSOIDAL);
				this.targets.setCoordinateSystem(TargetModel.ELLIPSOIDAL);
				
			} else if( event.getSource()==this.coordGeocentric) {
				this.pedestals.setCooordinateSystem(PedestalModel.GEOCENTRIC);
				this.targets.setCoordinateSystem(TargetModel.GEOCENTRIC);
			}
		} catch(Exception exception) {
			JOptionPane.showMessageDialog(this, exception.getMessage());
		}
	}
}
