/* 
 * java code for cobra assert exception ( placeholder currently)
 * in process of Converting from cobra to java code
 * Initially using java assertion till this is working
 */

package cobra.lang;

import java.util.List;


class AssertException extends Exception implements IHasSourceSite, HasAppendNonPropertyKeyValues
//		has DetailedStackTrace(false)
{
    Object _this;
    Object _info;   /* dynamic */
    SourceSite _sourceSite;
    java.util.List<Object> _expressions;
    
    AssertException(SourceSite sourceSite, java.util.List<Object> expressions /*dynamic */, Object thiss, Object info /*dynamic? */)
    {
        this(sourceSite, expressions, thiss, info, nil)
    }

    AssertException(SourceSite sourceSite, java.util.List<Object> expressions /*dynamic */, Object thiss, Object info /*as dynamic?*/, Exception innerExc)
    {
        super('assert', innerExc)
        _sourceSite = sourceSite
        _expressions = expressions
        _this = thiss
        _info = info
    }
// UNFINISHED
    
    Object getThis { return _this; }
	
    Object getInfo { return _info; }
		
    SourceSite getSourceSite { return _sourceSite; }
	
    List<Object> getExpressions { return _expressions; }

    @Override
    String getMessage()
    {
			nl = System.properties.newLine
			StringBuilder sb = new StringBuilder(nl);
			sb.append('sourceSite = [.sourceSite][nl]');
			sb.append('info       = [.makeString(.info)][nl]');
			sb.append('this       = [.makeString(.this)][nl]');
			int indentLevel = 1;
			int i = 1;
			List<Object> exprs = _expressions;
			while (i < exprs.size) {
				Object item = exprs.get(i));
				if (item == +1) {
					indentLevel += 1;
					i += 1;
				}
				else if (item == -1) {
					indentLevel -= 1;
					i += 1;
				}
				else {
					String source = (String)item;
					Object value = exprs.get(i+1);
					dirStr = value to? CobraDirectString
					valueString = if(dirStr, dirStr.string, .makeString(value))
					sb.append(String(' ', indentLevel*4))
					sb.append('[source] = [valueString][nl]');
					i += 2;
				}
			}

          return sb.toString
      }
	
      String makeString(Object obj /*dynamic? */)
      {
			String s;
			try {
				s = CobraCore.techStringMaker.makeString(obj);
			} 
			catch (Exception e) {
				s = 'CobraCore.techStringMaker.makeString exception: ' + e.message;
			}
			return s;
		}

		def appendNonPropertyKeyValues(target as HasAppendKeyValue )
			# Invoked by the Cobra Exception Report and CobraMain-ObjectExplorer-WinForms
			# By adding the expression breakdown as entries in the view,
			# object values will be clickable which will lead to their own detailed view.
			indentLevel = 0
			target.appendKeyValue('expression breakdown:', Html(''))
			i, exprs = 1, _expressions
			while i < exprs.count
				item = exprs[i]
				if item == +1
					indentLevel += 1
					i += 1
				else if item == -1
					indentLevel -= 1
					i += 1
				else
					source = item to String
					value = exprs[i+1]
					target.appendKeyValue(String(c' ', indentLevel*4)+source, value)
					i += 2

		def populateTreeWithExpressions(tree as ITreeBuilder)
			# Invoked by the Object Explorer, but any tool could use this by implementing ITreeBuilder.
			# By adding the expression breakdown as entries in the view,
			# object values will be clickable which will lead to their own detailed view.
			i, exprs = 1, _expressions
			while i < exprs.count
				item = exprs[i]
				if item == +1
					tree.indent
					i += 1
				else if item == -1
					tree.outdent
					i += 1
				else
					source = item to String
					value = exprs[i+1]
					tree.appendKeyValue(source, value)
					i += 2





/*
	## Exceptions about dynamic

	class DynamicOperationException inherits Exception
		"""
		The base class for all dynamic operation exceptions.
		"""

		cue init(message as String?)
			.init(message, nil)

		cue init(message as String?, innerExc as Exception?)
			base.init(message, innerExc)

	class CannotEnumerateException inherits DynamicOperationException
		
		# CC: axe init()s

		cue init(message as String?)
			.init(message, nil)

		cue init(message as String?, innerExc as Exception?)
			base.init(message, innerExc)
		
		
	class UnknownMemberException inherits DynamicOperationException

		var _obj as Object
		var _name as String
		var _type as Type

		cue init(obj as Object, name as String, type as Type)
			.init(obj, name, type, nil)

		cue init(obj as Object, name as String, type as Type, innerExc as Exception?)
			base.init('obj=[CobraCore.toTechString(obj)], name=[CobraCore.toTechString(name)], type=[type]', innerExc)
			_obj = obj
			_name = name
			_type = type


	class CannotReadPropertyException inherits UnknownMemberException

		# CC: axe init()s

		cue init(obj as Object, name as String, type as Type)
			.init(obj, name, type, nil)

		cue init(obj as Object, name as String, type as Type, innerExc as Exception?)
			base.init(obj, name, type, innerExc)


	class CannotWritePropertyException inherits UnknownMemberException

		# CC: axe init()s

		cue init(obj as Object, name as String, type as Type)
			.init(obj, name, type, nil)

		cue init(obj as Object, name as String, type as Type, innerExc as Exception?)
			base.init(obj, name, type, innerExc)


	class CannotSliceTypeException inherits UnknownMemberException

		# CC: axe init()s

		cue init(obj as Object, name as String, type as Type)
			.init(obj, name, type, nil)

		cue init(obj as Object, name as String, type as Type, innerExc as Exception?)
			base.init(obj, name, type, innerExc)


	class CannotInTypeException inherits UnknownMemberException

		# CC: axe init()s

		cue init(obj as Object, name as String, type as Type)
			.init(obj, name, type, nil)

		cue init(obj as Object, name as String, type as Type, innerExc as Exception?)
			base.init(obj, name, type, innerExc)


	class CannotCompareException inherits DynamicOperationException

		var _a
		var _b

		cue init(a, b)
			.init(a, b, nil)

		cue init(a, b, innerExc as Exception?)
			base.init('a=[a], b=[b]', innerExc)
			#base.init('a=[CobraCore.toTechString(a)], a.getType=[a.getType.name], b=[CobraCore.toTechString(b)], b.getType=[b.getType.name]', innerExc)
			_a = a
			_b = b


	## Assertions, contracts, etc.

	class AssertException inherits Exception implements IHasSourceSite, HasAppendNonPropertyKeyValues
		has DetailedStackTrace(false)

		cue init(sourceSite as SourceSite, expressions as IList<of dynamic>, thiss as Object, info as dynamic?)
			.init(sourceSite, expressions, thiss, info, nil)

		cue init(sourceSite as SourceSite, expressions as IList<of dynamic>, thiss as Object, info as dynamic?, innerExc as Exception?)
			base.init('assert', innerExc)
			_sourceSite = sourceSite
			_expressions = expressions
			_this = thiss
			_info = info

		get this from var as Object
	
		get info from var as dynamic?
		
		get sourceSite from var as SourceSite
	
		get expressions from var as IList<of dynamic>

		get message as String? is override
			nl = Environment.newLine
			sb = StringBuilder(nl)
			sb.append('sourceSite = [.sourceSite][nl]')
			sb.append('info       = [.makeString(.info)][nl]')
			sb.append('this       = [.makeString(.this)][nl]')
			i = indentLevel = 1
			exprs = _expressions
			while i < exprs.count
				item = exprs[i]
				if item == +1
					indentLevel += 1
					i += 1
				else if item == -1
					indentLevel -= 1
					i += 1
				else
					source = item to String
					value = exprs[i+1]
					dirStr = value to? CobraDirectString
					valueString = if(dirStr, dirStr.string, .makeString(value))
					sb.append(String(c' ', indentLevel*4))
					sb.append('[source] = [valueString][nl]')
					i += 2

			return sb.toString
	
		def makeString(obj as dynamic?) as String
			try
				s = CobraCore.techStringMaker.makeString(obj)
			catch e as Exception
				s = 'CobraCore.techStringMaker.makeString exception: ' + e.message
			return s

		def appendNonPropertyKeyValues(target as HasAppendKeyValue )
			# Invoked by the Cobra Exception Report and CobraMain-ObjectExplorer-WinForms
			# By adding the expression breakdown as entries in the view,
			# object values will be clickable which will lead to their own detailed view.
			indentLevel = 0
			target.appendKeyValue('expression breakdown:', Html(''))
			i, exprs = 1, _expressions
			while i < exprs.count
				item = exprs[i]
				if item == +1
					indentLevel += 1
					i += 1
				else if item == -1
					indentLevel -= 1
					i += 1
				else
					source = item to String
					value = exprs[i+1]
					target.appendKeyValue(String(c' ', indentLevel*4)+source, value)
					i += 2

		def populateTreeWithExpressions(tree as ITreeBuilder)
			# Invoked by the Object Explorer, but any tool could use this by implementing ITreeBuilder.
			# By adding the expression breakdown as entries in the view,
			# object values will be clickable which will lead to their own detailed view.
			i, exprs = 1, _expressions
			while i < exprs.count
				item = exprs[i]
				if item == +1
					tree.indent
					i += 1
				else if item == -1
					tree.outdent
					i += 1
				else
					source = item to String
					value = exprs[i+1]
					tree.appendKeyValue(source, value)
					i += 2


	class InvariantException inherits AssertException

		cue init(sourceSite as SourceSite, expressions as IList<of dynamic>, thiss as Object, info as dynamic?)
			.init(sourceSite, expressions, thiss, info, nil)

		cue init(sourceSite as SourceSite, expressions as IList<of dynamic>, thiss as Object, info as dynamic?, innerExc as Exception?)
			base.init(sourceSite, expressions, thiss, info, innerExc)


	class RequireException inherits AssertException

		cue init(sourceSite as SourceSite, expressions as IList<of dynamic>, thiss as Object, info as dynamic?)
			.init(sourceSite, expressions, thiss, info, nil)

		cue init(sourceSite as SourceSite, expressions as IList<of dynamic>, thiss as Object, info as dynamic?, innerExc as Exception?)
			base.init(sourceSite, expressions, thiss, info, innerExc)

		pro next from var as RequireException?


	class EnsureException inherits AssertException

		cue init(sourceSite as SourceSite, expressions as IList<of dynamic>, thiss as Object, info as dynamic?)
			.init(sourceSite, expressions, thiss, info, nil)

		cue init(sourceSite as SourceSite, expressions as IList<of dynamic>, thiss as Object, info as dynamic?, innerExc as Exception?)
			base.init(sourceSite, expressions, thiss, info, innerExc)


	class NonNilCastException inherits AssertException

		# it's unfortunate that we have to choose between inheriting AssertException or NullReferenceException

		cue init(sourceSite as SourceSite, expressions as IList<of dynamic>, thiss as Object, info as dynamic?)
			.init(sourceSite, expressions, thiss, info, nil)

		cue init(sourceSite as SourceSite, expressions as IList<of dynamic>, thiss as Object, info as dynamic?, innerExc as Exception?)
			base.init(sourceSite, expressions, thiss, info, innerExc)

		get message as String? is override
			return 'Cast to non-nil failed.[Environment.newLine][base.message]'


	## Misc exceptions

	class ExpectException inherits Exception

		cue init(expectedExceptionType as Type, actualException as Exception?)
			base.init
			_expectedExceptionType = expectedExceptionType
			_actualException = actualException
	
		get expectedExceptionType from var as Type
	
		get actualException from var as Exception?

		get message as String? is override
			sb = StringBuilder()
			sb.append('Expecting exception: [_expectedExceptionType.name], but ')
			if _actualException
				sb.append('a different exception was thrown: [_actualException]')
			else
				sb.append('no exception was thrown.')
			return sb.toString


	class FallThroughException inherits Exception

		cue init
			.init(nil)
			pass

		cue init(info as Object?)
			base.init
			_info = info

		cue init(info as Object?, innerExc as Exception?)
			base.init(nil, innerExc)
			_info = info

		get message as String is override
			return 'info=[CobraCore.toTechString(_info)]'

		get info from var as Object?


	class SliceException inherits SystemException

		cue init(msg as String?)
			base.init
*/
}